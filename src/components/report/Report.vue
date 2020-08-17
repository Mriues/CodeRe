<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据统计</el-breadcrumb-item>
      <el-breadcrumb-item>数据报表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card>
      <!-- 2.为ECharts准备一个具备大小（宽高）的Dom -->
      <div id="main"
           style="width: 750px;height:400px;"></div>
    </el-card>
  </div>
</template>

<script>
// 1.导入echarts
import echarts from 'echarts'

export default {
  data () {
    return {
      date: [],
      areaCount: [],
      area: []
    }
  },
  async created () {
    await this.getData()
    // 确保DOM更新数据后再执行
    this.$nextTick(() => {
      this.drawLine()
    })
  },
  // 此时页面上元素已经渲染完毕
  mounted () {

  },
  computed: {
    count () {
      if (this.areaCount.length !== 0) {
        return this.areaCount
      }
      return null
    }
  },
  methods: {
    async getData () {
      const { data: res } = await this.$http.get('reports')
      if (res.meta.status !== 200) {
        return this.$message.error('获取折线图数据失败！')
      }
      this.date = res.x
      this.areaCount = res.data
      res.data.forEach(item => {
        this.area.push(item.area)
      })
      console.log(res)
    },
    // 绘图方法
    drawLine () {
      var myChart = echarts.init(document.getElementById('main'))
      var series1 = []
      for (var i = 0; i < this.areaCount.length; i++) {
        // push向series1数组添加数据
        series1.push(
          {
            name: this.areaCount[i].area,
            type: 'line',
            stack: '总量',
            areaStyle: {},
            data: this.areaCount[i].user_count
          }
        )
      }

      var options = {
        title: {
          text: '用户来源'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#E9EEF3'
            }
          }
        },
        legend: {
          data: this.area
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: this.date
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: series1
      }

      // 5.展示数据
      myChart.setOption(options)
    }
  }
}
</script>

<style lang="less" scoped>
</style>
