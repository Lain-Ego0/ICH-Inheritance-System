<template>
    <div class="analyse-container">
        <!-- 顶部数据卡片 -->
        <el-row :gutter="20" class="data-cards">
            <el-col :span="8" v-for="(item, index) in statsCards" :key="index">
                <el-card class="stats-card">
                    <div class="card-content">
                        <div class="card-value">{{ item.value }}</div>
                        <div class="card-label">{{ item.label }}</div>
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 图表区域 -->
        <el-row :gutter="20" class="chart-row">
            <el-col :span="16">
                <el-card>
                    <div class="chart-title">话题发布趋势</div>
                    <div class="echart" id="trendChart" style="height: 350px"></div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card>
                    <div class="chart-title">话题质量分布</div>
                    <div class="echart" id="qualityChart" style="height: 350px"></div>
                </el-card>
            </el-col>
        </el-row>

        <el-row :gutter="20" class="chart-row">
            <el-col :span="12">
                <el-card>
                    <div class="chart-title">话题类型分布</div>
                    <div class="echart" id="typeChart" style="height: 350px"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div class="chart-title">用户活跃度排行</div>
                    <div class="echart" id="userActivityChart" style="height: 350px"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import * as echarts from "echarts";
import { mapGetters } from 'vuex';

export default {
    name: 'TopicSynthesizeAnalyse',
    computed: {
        ...mapGetters(['Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId']),
        statsCards() {
            return [
                { label: '平均浏览量', value: this.interactionStats?.avgViewCount?.toFixed(2) || '0' },
                { label: '平均评论数', value: this.interactionStats?.avgCommentCount?.toFixed(2) || '0' },
                { label: '平均收藏数', value: this.interactionStats?.avgCollectCount?.toFixed(2) || '0' }
            ];
        }
    },
    data() {
        return {
            interactionStats: {},
            charts: {}
        };
    },
    async mounted() {
        await this.TopicSynthesizeAnalyseApi();
        window.addEventListener('resize', this.handleResize);
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.handleResize);
        Object.values(this.charts).forEach(chart => chart.dispose());
    },
    methods: {
        handleResize() {
            Object.values(this.charts).forEach(chart => chart.resize());
        },
        async TopicSynthesizeAnalyseApi() {
            const { Data } = await this.$Post('/Topic/TopicSynthesizeAnalyse', {});
            this.interactionStats = Data.interactionStats;
            this.$nextTick(() => {
                this.initTrendChart(Data.trendData);
                this.initQualityChart(Data.qualityDistribution);
                this.initTypeChart(Data.typeDistribution);
                this.initUserActivityChart(Data.userActivity);
            });
        },
        initTrendChart(data) {
            const chart = echarts.init(document.getElementById('trendChart'));
            this.charts.trend = chart;

            const option = {
                backgroundColor: 'transparent',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'line',
                        lineStyle: {
                            color: 'rgba(64, 158, 255, 0.3)',
                            width: 2
                        }
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: data.map(item => item.date),
                    axisLabel: {
                        rotate: 45,
                        color: '#666'
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#ddd'
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    splitLine: {
                        lineStyle: {
                            color: 'rgba(64, 158, 255, 0.1)'
                        }
                    },
                    axisLabel: {
                        color: '#666'
                    }
                },
                series: [{
                    name: '话题数量',
                    type: 'line',
                    smooth: true,
                    symbol: 'circle',
                    symbolSize: 8,
                    data: data.map(item => item.count),
                    lineStyle: {
                        width: 4,
                        shadowColor: 'rgba(64, 158, 255, 0.5)',
                        shadowBlur: 10
                    },
                    itemStyle: {
                        color: 'var(--primary-color)',
                        borderWidth: 2,
                        borderColor: '#fff'
                    },
                    areaStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
                            { offset: 1, color: 'rgba(64, 158, 255, 0.1)' }
                        ])
                    }
                }]
            };
            chart.setOption(option);
        },
        initQualityChart(data) {
            const chart = echarts.init(document.getElementById('qualityChart'));
            this.charts.quality = chart;

            const option = {
                backgroundColor: 'transparent',
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    right: '5%',
                    top: 'center',
                    textStyle: {
                        color: '#666'
                    }
                },
                series: [{
                    type: 'pie',
                    radius: ['45%', '70%'],
                    center: ['40%', '50%'],
                    avoidLabelOverlap: false,
                    itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                    },
                    label: {
                        show: false
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '14',
                            fontWeight: 'bold'
                        },
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    data: [
                        {
                            value: data.high,
                            name: '高质量',
                            itemStyle: { color: '#91cc75' }
                        },
                        {
                            value: data.medium,
                            name: '中等',
                            itemStyle: { color: '#fac858' }
                        },
                        {
                            value: data.low,
                            name: '低质量',
                            itemStyle: { color: '#ee6666' }
                        }
                    ]
                }]
            };
            chart.setOption(option);
        },
        initTypeChart(data) {
            const chart = echarts.init(document.getElementById('typeChart'));
            this.charts.type = chart;

            const option = {
                backgroundColor: 'transparent',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '15%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.name),
                    axisLabel: {
                        rotate: 45,
                        color: '#666'
                    },
                    axisTick: { show: false },
                    axisLine: {
                        lineStyle: { color: '#ddd' }
                    }
                },
                yAxis: {
                    type: 'value',
                    splitLine: {
                        lineStyle: {
                            color: 'rgba(0,0,0,0.05)'
                        }
                    },
                    axisLabel: { color: '#666' }
                },
                series: [{
                    type: 'bar',
                    data: data.map(item => ({
                        value: item.value,
                        itemStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: '#1a98ff' },
                                { offset: 1, color: '#7049f0' }
                            ]),
                            borderRadius: [4, 4, 0, 0]
                        }
                    })),
                    barWidth: '40%',
                    showBackground: true,
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.1)',
                        borderRadius: [4, 4, 0, 0]
                    }
                }]
            };
            chart.setOption(option);
        },
        initUserActivityChart(data) {
            const chart = echarts.init(document.getElementById('userActivityChart'));
            this.charts.userActivity = chart;

            const sortedData = data
                .filter(item => item.activityScore > 0)
                .sort((a, b) => b.activityScore - a.activityScore)
                .slice(0, 5);

            const option = {
                backgroundColor: 'transparent',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    splitLine: {
                        lineStyle: {
                            color: 'rgba(0,0,0,0.05)'
                        }
                    },
                    axisLabel: { color: '#666' }
                },
                yAxis: {
                    type: 'category',
                    data: sortedData.map(item => item.userName),
                    axisLabel: {
                        color: '#666',
                        fontSize: 12
                    },
                    axisTick: { show: false },
                    axisLine: {
                        lineStyle: { color: '#ddd' }
                    }
                },
                series: [{
                    name: '活跃度',
                    type: 'bar',
                    data: sortedData.map(item => ({
                        value: item.activityScore,
                        itemStyle: {
                            color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                                { offset: 0, color: '#ff9a9e' },
                                { offset: 1, color: '#fad0c4' }
                            ]),
                            borderRadius: [0, 4, 4, 0]
                        }
                    })),
                    barWidth: '60%',
                    showBackground: true,
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.1)',
                        borderRadius: [0, 4, 4, 0]
                    },
                    label: {
                        show: true,
                        position: 'right',
                        color: '#666'
                    }
                }]
            };
            chart.setOption(option);
        }
    }
};
</script>

<style scoped>
/* 容器样式 */
.analyse-container {
    padding: 20px;
    background-color: #f5f7fa;
}

/* 数据卡片样式 */
.data-cards {
    margin-bottom: 20px;
}

.stats-card {
    text-align: center;
    padding: 20px;
}

.card-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.card-value {
    font-size: 24px;
    font-weight: bold;
    color: var(--primary-color);
    margin-bottom: 8px;
}

.card-label {
    font-size: 14px;
    color: #909399;
}

/* 图表容器样式 */
.chart-row {
    margin-bottom: 20px;
}

.chart-title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 16px;
    color: #303133;
}

.el-card {
    margin-bottom: 20px;
}
</style>

<style scoped>
/* 统一按钮颜色为侧边栏风格 */
.el-button--primary {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--primary:hover {
    background-color: #555;
    border-color: #555;
}

.el-button--warning {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--warning:hover {
    background-color: #555;
    border-color: #555;
}

.el-button--danger {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--danger:hover {
    background-color: #555;
    border-color: #555;
}
</style>