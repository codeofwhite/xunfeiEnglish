<template>
  <div class="book-chapter">
    <h1 class="book-title">{{ book.title }}</h1>
    <ul class="chapter-list">
      <li v-for="chapter in book.chapters" :key="chapter.id" class="chapter-item">
        <div class="chapter-card" @click="goToChapterDetail(chapter.id)">
          <h2 class="chapter-title">{{ chapter.title }}</h2>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    bookId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      book: {
        title: '',
        chapters: []
      }
    };
  },
  methods: {
    async fetchBookDetails() {
      try {
        console.log(this.bookId)
        // 从JSON文件中获取书本详情
        const response = await axios.get('/readBook/books.json');
        const books = response.data; // 假设JSON文件的结构是一个数组
        console.log(books)
        this.book = books.find(book => book.id == this.bookId) || {title: '', chapters: []};
        console.log('Selected book:', this.book);
      } catch (error) {
        console.error('获取书本详情失败:', error);
      }
    },
    goToChapterDetail(chapterId) {
      this.$router.push({name: 'ReadBookComponent', params: {chapterId: chapterId, bookId: this.bookId}});
    }
  },
  mounted() {
    this.fetchBookDetails();
  }
};
</script>

<style scoped>
.book-chapter {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.book-title {
  font-size: 28px;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.chapter-list {
  list-style-type: none;
  padding: 0;
}

.chapter-item {
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
}

.chapter-card {
  padding: 15px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  width: 100%;
  max-width: 600px;
  text-align: center;
}

.chapter-card:hover {
  background-color: #f1f1f1;
  transform: scale(1.02);
}

.chapter-title {
  font-size: 20px;
  color: #333;
}
</style>