<template>
  <div>
    <button @click="pushBack">回到首页</button>
    <h2>用户学习情况</h2>
    <div>
      <h3>智能背单词</h3>
      <canvas id="vocabBarChart"></canvas>
    </div>
    <div>
      <h3>智能语音对话</h3>
      <canvas id="speechLineChart"></canvas>
    </div>
    <div>
      <h3>智能英文书籍阅读</h3>
      <canvas id="readingPieChart"></canvas>
    </div>
  </div>
</template>

<script>
import {
  Chart,
  LinearScale,
  CategoryScale,
  BarController,
  BarElement,
  LineController,
  LineElement,
  PointElement,
  PieController,
  ArcElement,
  Tooltip,
  Legend
} from 'chart.js';
import router from "@/router/index.js";

Chart.register(LinearScale, CategoryScale, BarController, BarElement, LineController, LineElement, PointElement, PieController, ArcElement, Tooltip, Legend);

export default {
  mounted() {
    this.createBarChart('vocabBarChart', ['背单词时间', '登录频率', '逗留时间'], [30, 5, 120]);
    this.createLineChart('speechLineChart', ['1月', '2月', '3月', '4月', '5月'], [10, 15, 20, 25, 30]);
    this.createPieChart('readingPieChart', ['阅读时间', '登录频率', '逗留时间'], [45, 5, 90]);
  },
  methods: {
    pushBack() {
      this.$router.push('/');
    },
    createBarChart(chartId, labels, data) {
      const ctx = document.getElementById(chartId).getContext('2d');
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: labels,
          datasets: [{
            label: '智能背单词',
            data: data,
            backgroundColor: [
              'rgba(75, 192, 192, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)'
            ],
            borderColor: [
              'rgba(75, 192, 192, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          },
          plugins: {
            tooltip: {
              enabled: true,
              callbacks: {
                label: function (context) {
                  return `${context.label}: ${context.raw}`;
                }
              }
            }
          }
        }
      });
    },
    createLineChart(chartId, labels, data) {
      const ctx = document.getElementById(chartId).getContext('2d');
      new Chart(ctx, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [{
            label: '智能语音对话',
            data: data,
            fill: false,
            borderColor: 'rgba(75, 192, 192, 1)',
            tension: 0.1
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          },
          plugins: {
            tooltip: {
              enabled: true,
              callbacks: {
                label: function (context) {
                  return `${context.label}: ${context.raw}`;
                }
              }
            }
          }
        }
      });
    },
    createPieChart(chartId, labels, data) {
      const ctx = document.getElementById(chartId).getContext('2d');
      new Chart(ctx, {
        type: 'pie',
        data: {
          labels: labels,
          datasets: [{
            label: '智能英文书籍阅读',
            data: data,
            backgroundColor: [
              'rgba(75, 192, 192, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)'
            ],
            borderColor: [
              'rgba(75, 192, 192, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          plugins: {
            tooltip: {
              enabled: true,
              callbacks: {
                label: function (context) {
                  return `${context.label}: ${context.raw}`;
                }
              }
            }
          }
        }
      });
    }
  }
};
</script>

<style scoped>
h2, h3 {
  text-align: center;
}

canvas {
  display: block;
  margin: 0 auto 20px;
}
</style>