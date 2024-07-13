<template>
  <div id="learning-dashboard">
    <button @click="pushBack" class="back-button">回到首页</button>
    <h2>用户学习情况</h2>
    <div class="charts-wrapper">
      <div class="chart-container">
        <h3>智能背单词</h3>
        <canvas id="vocabBarChart"></canvas>
      </div>
      <div class="chart-container">
        <h3>智能语音对话</h3>
        <canvas id="speechLineChart"></canvas>
      </div>
    </div>
    <div class="charts-wrapper">
      <div class="chart-container">
        <h3>智能英文书籍阅读</h3>
        <canvas id="readingPieChart"></canvas>
      </div>
      <div class="chart-container">
        <h3>学习进度</h3>
        <canvas id="progressDoughnutChart"></canvas>
      </div>
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
  DoughnutController,
  Tooltip,
  Legend
} from 'chart.js';
import router from "@/router/index.js";

Chart.register(LinearScale, CategoryScale, BarController, BarElement, LineController, LineElement, PointElement, PieController, ArcElement, DoughnutController, Tooltip, Legend);

export default {
  mounted() {
    this.createBarChart('vocabBarChart', ['背单词时间', '登录频率', '逗留时间'], [30, 5, 120]);
    this.createLineChart('speechLineChart', ['1月', '2月', '3月', '4月', '5月'], [10, 15, 20, 25, 30]);
    this.createPieChart('readingPieChart', ['阅读时间', '登录频率', '逗留时间'], [45, 5, 90]);
    this.createDoughnutChart('progressDoughnutChart', ['完成度', '未完成'], [75, 25]);
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
    },
    createDoughnutChart(chartId, labels, data) {
      const ctx = document.getElementById(chartId).getContext('2d');
      new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: labels,
          datasets: [{
            label: '学习进度',
            data: data,
            backgroundColor: [
              'rgba(75, 192, 192, 0.2)',
              'rgba(255, 99, 132, 0.2)'
            ],
            borderColor: [
              'rgba(75, 192, 192, 1)',
              'rgba(255, 99, 132, 1)'
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
body {
  font-family: 'Arial', sans-serif;
  background-color: #f4f4f9;
  margin: 0;
  padding: 0;
}

#learning-dashboard {
  max-width: 900px;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2, h3 {
  text-align: center;
  color: #333;
  margin-bottom: 10px;
}

h2 {
  font-size: 1.8em;
  margin-top: 10px;
}

h3 {
  font-size: 1.4em;
  margin-top: 5px;
}

button {
  display: inline-block;
  padding: 8px 16px;
  margin: 10px auto;
  border: none;
  border-radius: 5px;
  background-color: #4CAF50;
  color: white;
  font-size: 0.9em;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #45a049;
}

.charts-wrapper {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.chart-container {
  padding: 15px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.chart-container h3 {
  margin-bottom: 5px;
}

.chart-container canvas {
  max-width: 100%;
}

@media (max-width: 600px) {
  h2 {
    font-size: 1.4em;
  }

  h3 {
    font-size: 1.1em;
  }

  button {
    padding: 6px 12px;
    font-size: 0.8em;
  }
}
</style>