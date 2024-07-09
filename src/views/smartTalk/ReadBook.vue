<template>
  <div id="app">
    <!-- Navigation and Search Bar -->
    <div class="container">
      <div>
        <button @click="goToHome" style="margin-bottom: 10px;">返回</button>
        <form @submit.prevent="searchBooks">
          <input type="text" v-model="searchQuery" placeholder="输入关键字搜索...">
          <button type="submit">确认</button>
        </form>
        <!-- Sidebar -->
        <div class="sidebar">
          <button @click="showBooks('热门推荐')">热门推荐</button>
          <button @click="showBooks('经典名著')">经典名著</button>
          <button @click="showBooks('寓言故事')">寓言故事</button>
        </div>
      </div>
      <!-- Content Area -->
      <div class="content">
        <!-- 书籍列表 -->
        <div v-for="book in filteredBooks" :key="book.id" class="book">
          <h2>{{ book.title }}</h2>
          <p>作者: {{ book.author }}</p>
          <img :src="'./images/' + book.image" alt="Book Cover">
          <p>{{ book.description }}</p>
          <button @click="goToBookDetails(book.id)">查看详情</button>
          <button @click="addToFavorites(book)">添加到收藏夹</button>
        </div>

        <!-- 收藏夹 -->
        <div class="favorites">
          <h2>书籍收藏夹</h2>
          <div v-for="favBook in favorites" :key="favBook.id" class="book-info">
            <h3>{{ favBook.title }}</h3>
            <p>作者: {{ favBook.author }}</p>
            <button @click="goToBookDetails(favBook.id)">查看详情</button>
            <button @click="removeFromFavorites(favBook)">从收藏夹移除</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      books: [
        {
          id: 1,
          title: 'Book 1',
          bookContent: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt. Ut labore et dolore magna aliqua.'
        },
        {
          id: 2,
          title: 'Book 2',
          bookContent: '这是第二本书的第一句. 这是第二本书的第二句. 这是第二本书的第三句. 这是第二本书的第四句. 这是第二本书的第五句. 这是第二本书的第六句. 这是第二本书的第七句. 这是第二本书的第八句.'
        }
      ],
      favorites: [],
      searchQuery: ''
      // ... 其他数据属性 ...
    };
  },
  methods: {
    goToHome() {
      // 导航至主页的逻辑
    },
    searchBooks() {
      // 搜索书籍的逻辑
    },
    showBooks(category) {
      // 显示书籍分类的逻辑
    },
    goToBookDetails(bookId) {
      // 导航至书籍详情页的逻辑
      // 使用`$router.push`方法导航到ReadBookComponent组件
      // 并传递bookId作为路由参数
      this.$router.push({ name: 'ReadBook', params: { bookId: bookId } });
    },
    addToFavorites(book) {
      // 添加到收藏夹的逻辑
    },
    removeFromFavorites(favBook) {
      // 从收藏夹移除的逻辑
    },
    fetchBooks() {
      // 来自 JSON 文件或 API 调用的模拟数据
      //下面是模拟的固定数据
      this.books = [
        {
          id: 1,
          title: 'Book Title 1',
          author: 'Author A',
          description: 'Description of Book 1',
          image: 'book1.jpg'
        },
        {
          id: 2,
          title: 'Book Title 2',
          author: 'Author B',
          description: 'Description of Book 2',
          image: 'book2.jpg'
        },
        {
          id: 3,
          title: 'Book Title 3',
          author: 'Author C',
          description: 'Description of Book 3',
          image: 'book3.jpg'
        },
        // Add more books as needed
      ];
    },
    // ... 其他方法 ...
  },
  computed: {
    filteredBooks() {
      // 计算过滤后的书籍列表
      if (this.searchQuery === '') {
        return this.books;
      } else {
        return this.books.filter(book =>
            book.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
            book.author.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      }
    }
    // ... 其他计算属性 ...
  },
  mounted() {
    // 组件挂载时的逻辑
    // 获取初始图书数据
    this.fetchBooks();
  }
  // ... 其他选项 ...
};
</script>

<style scoped>
/* Add your custom styles here */
body {
  font-family: Arial, sans-serif;
  margin: 20px;
}

.container {
  display: grid;
  grid-template-columns: 1fr 3fr;
  gap: 20px;
}

.sidebar {
  background-color: #f0f0f0;
  padding: 10px;
}

.content {
  padding: 10px;
}

.book {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}

.book img {
  max-width: 100px;
  float: left;
  margin-right: 10px;
}

.book-info {
  overflow: hidden;
}

.book-info h3 {
  margin-top: 0;
  font-size: 16px;
}

.book-info p {
  margin: 0;
  font-size: 14px;
}

.favorites {
  border: 1px solid #ccc;
  padding: 10px;
  margin-top: 20px;
}

/* ... 其他样式 ... */
</style>