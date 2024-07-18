<template>
  <div class="word-collection">
    <h1>不认识的单词</h1>
    <input v-model="searchQuery" placeholder="搜索单词..." class="search-input"/>
    <div class="word-list">
      <div v-for="(words, letter) in filteredWords" :key="letter" class="letter-section">
        <h2>{{ letter }}</h2>
        <div class="word-item" v-for="word in words" :key="word">
          <div class="word">{{ word }}</div>
          <div class="definition">{{ getDefinition(word) }}</div>
          <button @click="markAsMastered(word)">已掌握</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import axios from 'axios';
import {useStore} from 'vuex';

const unknownWords = ref([]);
const searchQuery = ref('');
const wordDefinitions = ref([]);

// 获取状态
const store = useStore();
const userEmail = computed(() => store.state.userEmail);

// 在组件挂载时调用后端接口
onMounted(async () => {
  try {
    const response = await axios({
      method: 'post',
      url: '/api/word/selectUnknown',
      params: {
        user_email: userEmail.value // 替换为实际的用户邮箱
      }
    });
    unknownWords.value = response.data; // 假设后端返回的是一个单词数组
    console.log('调用成功', response);
  } catch (error) {
    console.error('获取未掌握单词时出错:', error);
  }

  // 加载单词释义
  try {
    const response = await axios.get('/allWords.json');
    wordDefinitions.value = response.data;
  } catch (error) {
    console.error('加载单词释义时出错:', error);
  }
});

// 按字母顺序排序单词
const sortedWords = computed(() => {
  const grouped = unknownWords.value.reduce((acc, word) => {
    const firstLetter = word[0].toUpperCase();
    if (!acc[firstLetter]) {
      acc[firstLetter] = [];
    }
    acc[firstLetter].push(word);
    return acc;
  }, {});
  return Object.keys(grouped).sort().reduce((acc, letter) => {
    acc[letter] = grouped[letter];
    return acc;
  }, {});
});

// 根据搜索查询过滤单词
const filteredWords = computed(() => {
  if (!searchQuery.value) {
    return sortedWords.value;
  }
  const query = searchQuery.value.toLowerCase();
  const filtered = {};
  for (const letter in sortedWords.value) {
    const words = sortedWords.value[letter].filter(word => word.toLowerCase().includes(query));
    if (words.length) {
      filtered[letter] = words;
    }
  }
  return filtered;
});

// 获取单词释义
const getDefinition = (word) => {
  const definition = wordDefinitions.value.find(item => item.name === word);
  return definition ? definition.trans.join('; ') : '无释义';
};

// 将单词标记为已掌握
const markAsMastered = async (word) => {
  try {
    const response = await axios({
      method: 'post',
      url: '/api/word/removeUnknown',
      params: {
        user_email: userEmail.value,
        word_name: word
      }
    });
    if (response.data === 'success') {
      unknownWords.value = unknownWords.value.filter(w => w !== word);
      console.log('单词已标记为已掌握');
    } else {
      console.error('标记单词为已掌握失败');
    }
  } catch (error) {
    console.error('标记单词为已掌握时出错:', error);
  }
};
</script>

<style scoped>
.word-collection {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  background-color: #f0f0f0;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  color: #333;
}

.search-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.word-list {
  margin-top: 20px;
}

.letter-section {
  margin-bottom: 20px;
}

.letter-section h2 {
  margin-bottom: 10px;
  color: #5b8bf7;
}

.word-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
}

.word {
  font-size: 1.1em;
}

.definition {
  flex: 1;
  margin-left: 10px;
  color: #666;
}

button {
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

button:hover {
  background-color: #45a049;
  transform: scale(1.05);
}
</style>