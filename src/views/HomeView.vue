<template>
  <div class="app-container">
    <header class="app-header">
      <h1>English Mastery</h1>
      <p>Unlock the world with English</p>
    </header>
    <section class="features">
      <div class="feature" v-for="feature in features" :key="feature.title">
        <h2>{{ feature.title }}</h2>
        <p>{{ feature.description }}</p>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      features: [
        {title: 'Interactive Lessons', description: 'Engage with dynamic exercises and games.'},
        {title: 'Progress Tracking', description: 'Monitor your improvement with our analytics tools.'},
        {title: 'Community Support', description: 'Join discussions and learn together with peers.'}
      ]
    };
  }
};
</script>

<style scoped>
.app-container {
  text-align: center;
}

.app-header {
  background-color: #4CAF50;
  color: white;
  padding: 2rem;
}

.app-header h1 {
  animation: fadeInDown 2s ease;
}

.app-header p {
  animation: fadeInUp 2s ease;
}

.features {
  display: flex;
  justify-content: space-around;
  margin: 2rem 0;
}

.feature {
  transition: transform 0.3s ease;
}

.feature:hover {
  transform: scale(1.05);
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translate3d(0, -100%, 0);
  }
  to {
    opacity: 1;
    transform: none;
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translate3d(0, 100%, 0);
  }
  to {
    opacity: 1;
    transform: none;
  }
}
</style>