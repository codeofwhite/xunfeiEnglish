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
    fetchBookDetails() {
      // 模拟获取书本详情
      const books = [
        {
          id: 1,
          title: 'Book Title 1',
          chapters: [
            {id: 1, title: 'Chapter 1'},
            {id: 2, title: 'Chapter 2'}
          ]
        },
        {
          id: 2,
          title: 'Book Title 2',
          chapters: [
            {id: 3, title: 'Chapter 1'},
            {id: 4, title: 'Chapter 2'}
          ]
        },
        {
          id: 3,
          title: 'Book Title 3',
          chapters: [
            {id: 5, title: 'Chapter 1'},
            {id: 6, title: 'Chapter 2'}
          ]
        }
      ];
      // 找到对应的书本
      this.book = books.find(book => book.id == this.bookId) || {title: '', chapters: []};
    },
    goToChapterDetail(chapterId) {
      this.$router.push({name: 'ReadBookComponent', params: {chapterId: chapterId}});
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