<template>
  <div id="learning-dashboard">
    <button @click="pushBack" class="back-button">回到首页</button>
    <h2>用户学习情况分析</h2>
    <div class="charts-wrapper">
      <div class="smartTalk">
        <h2>智能语音对话</h2>
        <div class="chart-container">
          <h3>平均准确度</h3>
          <div id="accuracyChart" class="chart"></div>
        </div>
        <div class="chart-container">
          <h3>平均流利度</h3>
          <div id="fluencyChart" class="chart"></div>
        </div>
        <div class="chart-container">
          <h3>平均完整性</h3>
          <div id="integrityChart" class="chart"></div>
        </div>
      </div>
      <div class="vocabulary">
        <h2>生字数据分析</h2>
        <div class="chart-container">
          <h3>综合学习情况</h3>
          <div id="combinedChart" class="chart"></div>
        </div>
        <div class="chart-container">
          <h3>学习进度</h3>
          <div id="progressChart" class="chart"></div>
        </div>
        <div class="chart-container">
          <h3>单词掌握情况</h3>
          <div id="masteredChart" class="chart"></div>
        </div>
      </div>
      <div class="book">
        <h2>阅读数据分析</h2>
        <div class="chart-container">
          <h3>平均准确度</h3>
          <div id="accuracyBookChart" class="chart"></div>
        </div>
        <div class="chart-container">
          <h3>平均流利度</h3>
          <div id="fluencyBookChart" class="chart"></div>
        </div>
        <div class="chart-container">
          <h3>平均完整性</h3>
          <div id="integrityBookChart" class="chart"></div>
        </div>
        <h3>准确度趋势</h3>
        <div id="accuracyTrendChart" class="chart"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';

export default {
  data() {
    return {
      userEmail: 'user@example.com', // 替换为实际用户邮箱
    };
  },
  computed: {
    getUserEmail() {
      return this.$store.state.userEmail;
    }
  },
  mounted() {
    this.fetchAndCreateCharts();
  },
  methods: {
    pushBack() {
      this.$router.push('/');
    },
    async fetchAndCreateCharts() {
      try {
        const accuracyResponse = await axios.get(`http://localhost:8004/api/talk/averageAccuracy/${this.getUserEmail}`);
        const fluencyResponse = await axios.get(`http://localhost:8004/api/talk/averageFluency/${this.getUserEmail}`);
        const integrityResponse = await axios.get(`http://localhost:8004/api/talk/averageIntegrity/${this.getUserEmail}`);

        const accuracyBookResponse = await axios.get(`http://localhost:8005/api/bookTalk/averageAccuracy/${this.getUserEmail}`);
        const fluencyBookResponse = await axios.get(`http://localhost:8005/api/bookTalk/averageFluency/${this.getUserEmail}`);
        const integrityBookResponse = await axios.get(`http://localhost:8005/api/bookTalk/averageIntegrity/${this.getUserEmail}`);
        const accuracyTrendResponse = await axios.get(`http://localhost:8005/api/bookTalk/accuracyTrend/${this.getUserEmail}`);

        const unknownResponse = await axios.get(`http://localhost:8003/learning/unknownWords/${this.getUserEmail}`);
        const masteredResponse = await axios.get(`http://localhost:8003/learning/masteredWords/${this.getUserEmail}`);
        const progressResponse = await axios.get(`http://localhost:8003/learning/progress/${this.getUserEmail}`);

        const accuracy = accuracyResponse.data;
        const fluency = fluencyResponse.data;
        const integrity = integrityResponse.data;

        const accuracyBook = accuracyBookResponse.data;
        const fluencyBook = fluencyBookResponse.data;
        const integrityBook = integrityBookResponse.data;
        const accuracyTrend = accuracyTrendResponse.data;

        const unknownCount = unknownResponse.data;
        const masteredCount = masteredResponse.data;
        const progress = Array.isArray(progressResponse.data) ? progressResponse.data : [progressResponse.data];
        console.log(progress)

        // 计算学习进度的总和
        const totalProgress = progress.reduce((sum, value) => sum + value, 0);

        await this.$nextTick(() => {
          this.createBarChart('accuracyChart', '平均准确度', accuracy);
          this.createBarChart('fluencyChart', '平均流利度', fluency);
          this.createBarChart('integrityChart', '平均完整性', integrity);
          this.createCombinedChart('combinedChart', ['未知单词', '掌握单词', '学习进度'], [unknownCount, masteredCount, totalProgress]);
          this.createLineChart('progressChart', '学习进度', progress);
          this.createPieChart('masteredChart', ['未知单词', '掌握单词'], [unknownCount, masteredCount]);
          this.createBarChart('accuracyBookChart', '平均准确度', accuracyBook);
          this.createBarChart('fluencyBookChart', '平均流利度', fluencyBook);
          this.createBarChart('integrityBookChart', '平均完整性', integrityBook);
          this.createLineChart('accuracyTrendChart', '准确度趋势', accuracyTrend);
        });
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    createBarChart(chartId, label, data) {
      const chart = echarts.init(document.getElementById(chartId));
      const option = {
        title: {
          text: label,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: [label],
          axisTick: {
            alignWithLabel: true
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: [data],
          type: 'bar',
          barWidth: '60%',
          itemStyle: {
            color: '#4CAF50'
          },
          label: {
            show: true,
            position: 'top'
          }
        }]
      };
      // 防止路由跳转后不显示的问题
      document.getElementById(chartId).setAttribute('_echarts_instance_', '');
      chart.setOption(option);
    },
    createCombinedChart(chartId, labels, data) {
      const chart = echarts.init(document.getElementById(chartId));
      const option = {
        title: {
          text: '用户学习情况',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: labels,
          axisTick: {
            alignWithLabel: true
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: data,
          type: 'bar',
          barWidth: '60%',
          itemStyle: {
            color: '#FF9800'
          },
          label: {
            show: true,
            position: 'top'
          }
        }]
      };
      // 防止路由跳转后不显示的问题
      document.getElementById(chartId).setAttribute('_echarts_instance_', '');
      chart.setOption(option);
    },
    createLineChart(chartId, label, data) {
      const chart = echarts.init(document.getElementById(chartId));
      const option = {
        title: {
          text: label,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: data.map((_, index) => `Day ${index + 1}`)
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: data,
          type: 'line',
          itemStyle: {
            color: '#2196F3'
          },
          label: {
            show: true,
            position: 'top'
          }
        }]
      };
      // 防止路由跳转后不显示的问题
      document.getElementById(chartId).setAttribute('_echarts_instance_', '');
      chart.setOption(option);
    },
    createPieChart(chartId, labels, data) {
      const chart = echarts.init(document.getElementById(chartId));
      const option = {
        title: {
          text: '单词掌握情况',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [{
          type: 'pie',
          radius: '50%',
          data: labels.map((label, index) => ({
            value: data[index],
            name: label
          })),
          itemStyle: {
            color: (params) => {
              const colors = ['#FF5722', '#8BC34A'];
              return colors[params.dataIndex];
            }
          },
          label: {
            show: true,
            formatter: '{b}: {c} ({d}%)'
          }
        }]
      };
      // 防止路由跳转后不显示的问题
      document.getElementById(chartId).setAttribute('_echarts_instance_', '');
      chart.setOption(option);
    }
  }
};
</script>

<style scoped>
#learning-dashboard {
  padding: 20px;
}

.back-button {
  margin-bottom: 20px;
}

.charts-wrapper {
  display: initial;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  flex-direction: column;
}

.chart-container {
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
  display: inline-block;
  width: 30%;
}

.chart-container:hover {
  transform: translateY(-5px);
}

.chart-container:nth-child(3n) {
  margin-right: 0;
}

.chart {
  width: 100%;
  height: 400px; /* 确保画布有足够的高度 */
  align-items: center;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
  font-size: 2em;
}

button {
  display: inline-block;
  padding: 10px 20px;
  margin: 10px auto;
  border: none;
  border-radius: 5px;
  background-color: #4CAF50;
  color: white;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

button:hover {
  background-color: #45a049;
  transform: scale(1.05);
}

.smartTalk, .vocabulary {
  margin-bottom: 40px;
}
</style>