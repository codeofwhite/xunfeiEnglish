<template>
  <div class="favorites-container">
    <h2>收藏的单词</h2>
    <ul>
      <li v-for="word in favoriteWords" :key="word.id">
        <div class="word-details">
          <h3>{{ word.userFavor }}</h3>
          <button @click="removeFromFavorites(word)">移除收藏</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      favoriteWords: []
    };
  },
  methods: {
    async fetchFavoriteWords() {
      try {
        const response = await axios.get('http://114.132.52.232:8002/userFavor/list');
        this.favoriteWords = response.data.map(favor => ({
          id: favor.id,
          userFavor: favor.user_favor
        }));
      } catch (error) {
        console.error('获取收藏内容失败:', error);
      }
    },
    removeFromFavorites(word) {
      this.favoriteWords = this.favoriteWords.filter(favWord => favWord.id !== word.id);
    }
  },
  mounted() {
    this.fetchFavoriteWords();
  }
};
</script>

<style scoped>
.favorites-container {
  padding: 20px;
  background-color: #f0f4f8;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: 0 auto;
}

h2 {
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.word-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

h3 {
  margin: 0;
  color: #1a73e8;
}

button {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  background-color: #e74c3c;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #c0392b;
}
</style>