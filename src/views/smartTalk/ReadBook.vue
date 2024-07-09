<template>
  <div id="app" class="app">
    <header class="app-header">
      <button @click="goToHome" class="home-button">返回</button>
      <form @submit.prevent="searchBooks" class="search-form">
        <input type="text" v-model="searchQuery" placeholder="输入关键字搜索..." class="search-input">
        <button type="submit" class="search-button">确认</button>
      </form>
    </header>
    <aside class="sidebar">
      <button @click="showBooks('热门推荐')" class="category-button">热门推荐</button>
      <button @click="showBooks('经典名著')" class="category-button">经典名著</button>
      <button @click="showBooks('寓言故事')" class="category-button">寓言故事</button>
    </aside>
    <main class="content">
      <section class="book-list">
        <div v-for="book in filteredBooks" :key="book.id" class="book-card">
          <img :src="book.image" alt="Book Cover" class="book-image">
          <div class="book-details">
            <h2 class="book-title">{{ book.title }}</h2>
            <p class="book-author">作者: {{ book.author }}</p>
            <p class="book-description">{{ book.description }}</p>
            <div class="book-actions">
              <button @click="goToBookDetails(book.id)" class="details-button">查看详情</button>
              <button @click="addToFavorites(book)" class="favorites-button">添加到收藏夹</button>
            </div>
          </div>
        </div>
      </section>
      <section class="favorites-section">
        <h2 class="favorites-title">书籍收藏夹</h2>
        <div v-for="favBook in favorites" :key="favBook.id" class="favorites-card">
          <div class="book-details">
            <h3 class="favorites-book-title">{{ favBook.title }}</h3>
            <img :src="favBook.image" alt="Book Cover" class="book-image">
            <p class="favorites-book-author">作者: {{ favBook.author }}</p>
            <div class="favorites-actions">
              <button @click="goToBookDetails(favBook.id)" class="details-button">查看详情</button>
              <button @click="removeFromFavorites(favBook)" class="remove-button">从收藏夹移除</button>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 书本信息
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
      // 收藏的书的列表
      favorites: [
        {
          id: 2,
          title: 'Book 2',
          bookContent: '这是第二本书的第一句. 这是第二本书的第二句. 这是第二本书的第三句. 这是第二本书的第四句. ' +
              '这是第二本书的第五句. 这是第二本书的第六句. 这是第二本书的第七句. 这是第二本书的第八句.',
          image: 'src/assets/images/codeofwhite.jpg'
        }
      ],
      // 搜索
      searchQuery: ''
      // ... 其他数据属性 ...
    };
  },
  methods: {
    // 回到网页首页
    goToHome() {
      this.$router.push('/');
    },
    // 搜索书籍
    searchBooks() {
      // 搜索书籍的逻辑
    },
    // 显示书籍分类的逻辑
    showBooks(category) {
      // 显示书籍分类的逻辑
    },
    // 去到对应书本的阅读页面
    goToBookDetails(bookId) {
      // 导航至书籍详情页的逻辑
      // 使用`$router.push`方法导航到ReadBookComponent组件
      // 并传递bookId作为路由参数
      this.$router.push({name: 'ReadBook', params: {bookId: bookId}});
    },
    // 添加到收藏夹
    addToFavorites(book) {
      // 添加到收藏夹的逻辑
    },
    // 从收藏夹中移除
    removeFromFavorites(favBook) {
      // 从收藏夹移除的逻辑
    },
    // 获得书本
    fetchBooks() {
      // 来自 JSON 文件或 API 调用的模拟数据
      //下面是模拟的固定数据
      this.books = [
        {
          id: 1,
          title: 'Book Title 1',
          author: 'Author A',
          description: 'Description of Book 1',
          image: 'src/assets/images/codeofwhite.jpg'
        },
        {
          id: 2,
          title: 'Book Title 2',
          author: 'Author B',
          description: 'Description of Book 2',
          image: 'src/assets/images/codeofwhite.jpg'
        },
        {
          id: 3,
          title: 'Book Title 3',
          author: 'Author C',
          description: 'Description of Book 3',
          image: 'src/assets/images/codeofwhite.jpg'
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
.app {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
}

.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.home-button, .search-button, .category-button, .details-button, .favorites-button, .remove-button {
  padding: 0.5rem 1rem;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  margin: 0.5rem;
  border-radius: 0.25rem;
}

.home-button:hover, .search-button:hover, .category-button:hover, .details-button:hover, .favorites-button:hover, .remove-button:hover {
  background-color: #0056b3;
}

.search-form {
  display: flex;
}

.search-input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 0.25rem;
  margin-right: 0.5rem;
}

.sidebar {
  background-color: #f8f9fa;
  padding: 1rem;
  min-width: 200px;
}

.content {
  padding: 1rem;
  flex: 1;
}

.book-list, .favorites-section {
  margin-top: 1rem;
}

.book-card, .favorites-card {
  background-color: #fff;
  padding: 1rem;
  margin-bottom: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 0.25rem;
  display: flex;
  align-items: center;
}

.book-image {
  width: 100px;
  height: 150px;
  object-fit: cover;
  margin-right: 1rem;
}

.book-details, .favorites-card {
  flex: 1;
}

.book-title, .favorites-book-title {
  font-size: 1.25rem;
  margin-bottom: 0.5rem;
}

.book-author, .favorites-book-author {
  font-size: 1rem;
  color: #666;
}

.book-description {
  font-size: 0.875rem;
  margin-bottom: 1rem;
}

.book-actions, .favorites-actions {
  text-align: right;
}

.favorites-title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

/* ... 其他样式 ... */
</style>