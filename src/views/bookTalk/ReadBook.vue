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
      <button @click="showBooks('')" class="category-button">全部</button>
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
              <button @click="goToBookChapter(book.id)" class="details-button">查看详情</button>
              <button @click="addToFavorites(book)" class="favorites-button">添加到收藏夹</button>
            </div>
          </div>
        </div>
      </section>
      <section class="favorites-section">
        <h2 class="favorites-title">书籍收藏夹</h2>
        <div v-for="favBook in favoriteBooks" :key="favBook.id" class="favorites-card">
          <div class="book-details">
            <h3 class="favorites-book-title">{{ favBook.title }}</h3>
            <img :src="favBook.image" alt="Book Cover" class="book-image">
            <p class="favorites-book-author">作者: {{ favBook.author }}</p>
            <p class="book-description">{{ favBook.description }}</p>
            <div class="favorites-actions">
              <button @click="goToBookChapter(favBook.id)" class="details-button">查看详情</button>
              <button @click="removeFromFavorites(favBook)" class="remove-button">从收藏夹移除</button>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      // 书本信息
      books: [],
      // 收藏的书的列表
      favorites: [],
      // 显示的收藏的，用于显示
      favoriteBooks: [],
      // 搜索
      searchQuery: '',
      // 当前选择的分类
      selectedCategory: ''
    };
  },
  methods: {
    // 回到网页首页
    goToHome() {
      this.$router.push('/');
    },
    // 显示书籍分类的逻辑
    showBooks(category) {
      this.selectedCategory = category;
    },
    // 去到章节页面
    goToBookChapter(bookId) {
      this.$router.push({name: 'BookChapter', params: {bookId: bookId}});
    },
    // 获取收藏夹里面的书
    async fetchFavorites() {
      try {
        const response = await axios.get('http://localhost:8005/favorites/list', {
          params: {
            userEmail: 'user@example.com' // 替换为实际用户邮箱
          }
        });
        this.favorites = response.data;
        this.updateFavoriteBooks();
      } catch (error) {
        console.error('获取收藏夹失败:', error);
      }
    },
    // 从fetchBook的结果中显示favorBook
    updateFavoriteBooks() {
      this.favoriteBooks = this.books.filter(book =>
          this.favorites.some(fav => fav.bookId == book.id)
      );
    },
    // 添加到收藏夹
    async addToFavorites(book) {
      try {
        const response = await axios.post('http://localhost:8005/favorites/add', {
          bookId: book.id,
          userEmail: 'user@example.com', // 替换为实际用户邮箱
          createTime: new Date()
        });
        if (response.data) {
          this.favorites.push(book);
          this.updateFavoriteBooks();
        }
      } catch (error) {
        console.error('添加到收藏夹失败:', error);
      }
    },
    async removeFromFavorites(favBook) {
      try {
        await axios.delete('http://localhost:8005/favorites/remove', {
          params: {
            bookId: favBook.id,
            userEmail: 'user@example.com' // 替换为实际用户邮箱
          }
        });
        this.favorites = this.favorites.filter(book => book.id !== favBook.id);
        this.updateFavoriteBooks();
      } catch (error) {
        console.error('从收藏夹移除失败:', error);
      }
    },
    // 获得书本
    fetchBooks() {
      // 来自 JSON 文件或 API 调用的模拟数据
      // 这里使用固定数据作为示例
      this.books = [
        {
          id: 1,
          title: 'Book Title 1',
          author: 'Author A',
          description: 'Description of Book 1',
          category: '热门推荐',
          image: 'src/assets/images/codeofwhite.jpg'
        },
        {
          id: 2,
          title: 'Book Title 2',
          author: 'Author B',
          description: 'Description of Book 2',
          category: '经典名著',
          image: 'src/assets/images/codeofwhite.jpg'
        },
        {
          id: 3,
          title: 'Book Title 3',
          author: 'Author C',
          description: 'Description of Book 3',
          category: '寓言故事',
          image: 'src/assets/images/codeofwhite.jpg'
        }
      ];
    }
  },
  computed: {
    // 搜索和分类过滤
    filteredBooks() {
      return this.books.filter(book => {
        // 检查书籍是否匹配当前选择的分类
        const matchesCategory = this.selectedCategory ? book.category === this.selectedCategory : true;
        const matchesSearch = this.searchQuery ?
            book.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
            book.author.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
            book.description.toLowerCase().includes(this.searchQuery.toLowerCase()) : true;
        // 最终返回的是一个包含所有符合条件的书籍对象的数组，而不是布尔值 true
        return matchesCategory && matchesSearch;
      });
    }
  },
  mounted() {
    // 获取初始图书数据
    this.fetchBooks();
    this.fetchFavorites();
  }
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