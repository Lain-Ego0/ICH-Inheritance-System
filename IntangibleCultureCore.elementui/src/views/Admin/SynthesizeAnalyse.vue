<template>
    <div class="synthesize-analyse">
        <!-- 顶部统计卡片 -->
        <el-row :gutter="20" class="stat-cards">
            <el-col :span="6" v-for="(item, index) in statCards" :key="index">
                <el-card shadow="hover" class="stat-card">
                    <div class="card-content">
                        <div class="card-icon" :class="item.icon"></div>
                        <div class="card-info">
                            <div class="card-title">{{ item.title }}</div>
                            <div class="card-value">{{ item.value }}</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 主要图表区域 -->
        <el-row :gutter="20" class="chart-row">
            <!-- 左侧地图 -->
            <el-col :span="12">
                <el-card shadow="hover" class="chart-card">
                    <div slot="header" class="chart-header">
                        <span>非遗文化地区分布</span>
                        <el-tooltip content="展示各省份非遗文化项目数量分布" placement="top">
                            <i class="el-icon-info"></i>
                        </el-tooltip>
                    </div>
                    <div class="echart" id="mapChart" style="height: 500px;"></div>
                </el-card>
            </el-col>

            <!-- 右侧类型分布 -->
            <el-col :span="12">
                <el-card shadow="hover" class="chart-card">
                    <div slot="header" class="chart-header">
                        <span>非遗文化类型分布</span>
                        <el-tooltip content="展示不同类型非遗文化的数量占比" placement="top">
                            <i class="el-icon-info"></i>
                        </el-tooltip>
                    </div>
                    <div class="echart" id="typeChart" style="height: 500px;"></div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 第二行图表 -->
        <el-row :gutter="20" class="chart-row">
            <!-- 增长趋势 -->
            <el-col :span="16">
                <el-card shadow="hover" class="chart-card">
                    <div slot="header" class="chart-header">
                        <span>非遗文化增长趋势</span>
                        <el-tooltip content="展示各年份非遗文化数量及增长率" placement="top">
                            <i class="el-icon-info"></i>
                        </el-tooltip>
                    </div>
                    <div class="echart" id="trendChart" style="height: 400px;"></div>
                </el-card>
            </el-col>

            <!-- 批次分布 -->
            <el-col :span="8">
                <el-card shadow="hover" class="chart-card">
                    <div slot="header" class="chart-header">
                        <span>批次分布</span>
                        <el-tooltip content="展示不同批次非遗文化的数量分布" placement="top">
                            <i class="el-icon-info"></i>
                        </el-tooltip>
                    </div>
                    <div class="echart" id="batchChart" style="height: 400px;"></div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 第三行图表 -->
        <el-row :gutter="20" class="chart-row">
            <!-- 保护单位统计 -->
            <el-col :span="12">
                <el-card shadow="hover" class="chart-card">
                    <div slot="header" class="chart-header">
                        <span>保护单位统计</span>
                        <el-tooltip content="展示各保护单位的非遗文化项目数量" placement="top">
                            <i class="el-icon-info"></i>
                        </el-tooltip>
                        <el-select v-model="protectionUnitLimit" size="mini" style="width: 120px; float: right">
                            <el-option label="显示全部" :value="-1"></el-option>
                            <el-option label="显示前5项" :value="5"></el-option>
                            <el-option label="显示前10项" :value="10"></el-option>
                        </el-select>
                    </div>
                    <div class="echart" id="protectionUnitChart" style="height: 400px;"></div>
                </el-card>
            </el-col>

            <!-- 申报地统计 -->
            <el-col :span="12">
                <el-card shadow="hover" class="chart-card">
                    <div slot="header" class="chart-header">
                        <span>申报地统计</span>
                        <el-tooltip content="展示各申报地的非遗文化项目数量" placement="top">
                            <i class="el-icon-info"></i>
                        </el-tooltip>
                    </div>
                    <div class="echart" id="declarationPlaceChart" style="height: 400px;"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import proviceJson from '@/utils/provice.json';
import * as echarts from "echarts";
import { mapGetters } from 'vuex';

export default {
    name: 'SynthesizeAnalyse',
    computed: {
        ...mapGetters(['Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'])
    },
    data() {
        return {
            statCards: [],
            chartInstances: {},
            analysisData: null,
            protectionUnitLimit: 10,
            chartTheme: {
                // 自定义主题色
                colorPalette: ['#D48265', '#91C7AE', '#E16B8C', '#BFCEE0', '#749F83', '#CA8622', '#6E7074', '#A65E2E', '#BDA29A'],
                backgroundColor: 'transparent'
            }
        };
    },
    watch: {
        protectionUnitLimit() {
            this.renderProtectionUnitChart();
        }
    },
    async mounted() {
        await this.SynthesizeAnalyseApi();
        window.addEventListener("resize", this.resizeAllCharts);
    },
    beforeDestroy() {
        window.removeEventListener("resize", this.resizeAllCharts);
        // 销毁所有图表实例
        Object.values(this.chartInstances).forEach(chart => {
            chart && chart.dispose();
        });
    },
    methods: {
        async SynthesizeAnalyseApi() {
            try {
                const { Data } = await this.$Post('/Cultural/CulturalAnalyse', {});
                this.analysisData = Data;

                // 初始化统计卡片数据
                this.initStatCards();

                // 渲染所有图表
                this.$nextTick(() => {
                    this.initCharts();
                });
            } catch (error) {
                console.error("获取数据失败:", error);
                this.$message.error("获取分析数据失败，请稍后重试");
            }
        },

        initStatCards() {
            if (!this.analysisData || !this.analysisData.overallStatistics) return;

            const stats = this.analysisData.overallStatistics;
            this.statCards = [
                { title: '非遗项目总数', value: stats.totalCount, icon: 'el-icon-collection' },
                { title: '类型数量', value: stats.typeCount, icon: 'el-icon-s-grid' },
                { title: '覆盖地区数', value: stats.regionCount, icon: 'el-icon-location' },
                { title: '传承人数量', value: stats.inheritorCount, icon: 'el-icon-user' }
            ];
        },

        initCharts() {
            // 初始化各个图表
            this.renderMapChart();
            this.renderTypeChart();
            this.renderTrendChart();
            this.renderBatchChart();
            this.renderProtectionUnitChart();
            this.renderDeclarationPlaceChart();
        },

        // 地区分布地图
        renderMapChart() {
            const chartDom = document.getElementById('mapChart');
            if (!chartDom) return;

            const myChart = echarts.init(chartDom);
            this.chartInstances.mapChart = myChart;

            echarts.registerMap('china', proviceJson);

            const regionData = this.analysisData.regionStatistics || {};
            const data = Object.keys(regionData).map(name => {
                return { name, value: regionData[name] };
            });

            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} 项'
                },
                visualMap: {
                    min: 0,
                    max: Math.max(...data.map(item => item.value)),
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],
                    calculable: true,
                    inRange: {
                        color: ['#f7f6ed', '#e4ddcb', '#d6c6aa', '#c8ad8d', '#a78b6d', '#8c6a4f']
                    }
                },
                series: [
                    {
                        name: '非遗文化分布',
                        type: 'map',
                        map: 'china',
                        roam: true,
                        emphasis: {
                            label: {
                                show: true
                            }
                        },
                        data: data,
                        // 添加特效
                        rippleEffect: {
                            period: 4,
                            scale: 4,
                            brushType: 'fill'
                        }
                    }
                ]
            };

            myChart.setOption(option);
        },

        // 类型分布饼图
        renderTypeChart() {
            const chartDom = document.getElementById('typeChart');
            if (!chartDom) return;

            const myChart = echarts.init(chartDom);
            this.chartInstances.typeChart = myChart;

            const typeData = this.analysisData.typeStatistics || {};
            const data = Object.keys(typeData).map(name => {
                return { name, value: typeData[name] };
            });

            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    right: 10,
                    top: 'center',
                    data: data.map(item => item.name)
                },
                series: [
                    {
                        name: '类型分布',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        center: ['40%', '50%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '18',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: data,
                        // 添加特效
                        animationType: 'scale',
                        animationEasing: 'elasticOut',
                        animationDelay: function (idx) {
                            return Math.random() * 200;
                        },
                        color: this.chartTheme.colorPalette
                    }
                ]
            };

            myChart.setOption(option);
        },

        // 增长趋势图
        renderTrendChart() {
            const chartDom = document.getElementById('trendChart');
            if (!chartDom) return;

            const myChart = echarts.init(chartDom);
            this.chartInstances.trendChart = myChart;

            const trendData = this.analysisData.growthTrend || [];
            const years = trendData.map(item => item.year);
            const counts = trendData.map(item => item.count);
            const rates = trendData.map(item => item.growthRate);

            const option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        crossStyle: {
                            color: '#999'
                        }
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                legend: {
                    data: ['项目数量', '增长率']
                },
                xAxis: [
                    {
                        type: 'category',
                        data: years,
                        axisPointer: {
                            type: 'shadow'
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '项目数量',
                        min: 0,
                        axisLabel: {
                            formatter: '{value}'
                        }
                    },
                    {
                        type: 'value',
                        name: '增长率(%)',
                        axisLabel: {
                            formatter: '{value}%'
                        }
                    }
                ],
                series: [
                    {
                        name: '项目数量',
                        type: 'bar',
                        barWidth: '40%',
                        data: counts,
                        itemStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: '#D48265' },
                                { offset: 0.5, color: '#B25D4E' },
                                { offset: 1, color: '#8C4B3C' }
                            ])
                        }
                    },
                    {
                        name: '增长率',
                        type: 'line',
                        yAxisIndex: 1,
                        data: rates,
                        symbol: 'circle',
                        symbolSize: 8,
                        lineStyle: {
                            width: 3
                        },
                        itemStyle: {
                            color: '#91C7AE'
                        }
                    }
                ]
            };

            myChart.setOption(option);
        },

        // 批次分布图
        renderBatchChart() {
            const chartDom = document.getElementById('batchChart');
            if (!chartDom) return;

            const myChart = echarts.init(chartDom);
            this.chartInstances.batchChart = myChart;

            const batchData = this.analysisData.batchStatistics || {};
            const data = Object.keys(batchData).map(name => {
                return { name, value: batchData[name] };
            });

            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} 项 ({d}%)'
                },
                series: [
                    {
                        name: '批次分布',
                        type: 'pie',
                        radius: '75%',
                        center: ['50%', '50%'],
                        data: data.sort((a, b) => a.value - b.value),
                        roseType: 'radius',
                        label: {
                            formatter: '{b}: {c}'
                        },
                        itemStyle: {
                            borderRadius: 5
                        },
                        // 明确指定颜色数组
                        color: this.chartTheme.colorPalette,
                        // 添加特效
                        animationType: 'scale',
                        animationEasing: 'elasticOut'
                    }
                ]
            };

            myChart.setOption(option);
        },

        // 保护单位统计图
        renderProtectionUnitChart() {
            const chartDom = document.getElementById('protectionUnitChart');
            if (!chartDom) return;

            const myChart = echarts.init(chartDom);
            this.chartInstances.protectionUnitChart = myChart;

            const unitData = this.analysisData.protectionUnitStatistics || {};
            let data = Object.keys(unitData).map(name => {
                return { name, value: unitData[name] };
            });

            // 根据限制显示数量
            if (this.protectionUnitLimit > 0 && data.length > this.protectionUnitLimit) {
                data = data
                    .sort((a, b) => b.value - a.value)
                    .slice(0, this.protectionUnitLimit);
            }

            const option = {
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
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    data: data.map(item => item.name),
                    axisLabel: {
                        width: 100,
                        overflow: 'truncate',
                        interval: 0
                    }
                },
                series: [
                    {
                        name: '项目数量',
                        type: 'bar',
                        data: data.map(item => item.value),
                        itemStyle: {
                            color: function (params) {
                                // 使用非遗主题色彩
                                const colorList = ['#D48265', '#91C7AE', '#E16B8C', '#BFCEE0', '#749F83', '#CA8622', '#6E7074', '#A65E2E', '#BDA29A'];
                                return colorList[params.dataIndex % colorList.length];
                            }
                        }
                    }
                ]
            };

            myChart.setOption(option);
        },

        // 申报地统计图
        renderDeclarationPlaceChart() {
            const chartDom = document.getElementById('declarationPlaceChart');
            if (!chartDom) return;

            const myChart = echarts.init(chartDom);
            this.chartInstances.declarationPlaceChart = myChart;

            const placeData = this.analysisData.declarationPlaceStatistics || {};
            const data = Object.keys(placeData).map(name => {
                return { name, value: placeData[name] };
            });

            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} 项'
                },
                series: [
                    {
                        name: '申报地统计',
                        type: 'pie',
                        radius: '65%',
                        center: ['50%', '50%'],
                        data: data,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },
                        label: {
                            formatter: '{b}: {c} 项',
                            color: this.chartTheme.colorPalette
                        }
                    }
                ]
            };

            myChart.setOption(option);
        },

        // 响应窗口大小变化，调整所有图表
        resizeAllCharts() {
            Object.values(this.chartInstances).forEach(chart => {
                chart && chart.resize();
            });
        }
    }
}
</script>

<style scoped>
/* 整体容器样式 */
.synthesize-analyse {
    padding: 20px;
    background-color: #f9f6f0;
    /* 更改为米黄色背景，更符合非遗文化氛围 */
}

/* 统计卡片样式 */
.stat-cards {
    margin-bottom: 20px;
}

.stat-card {
    height: 120px;
    transition: all 0.3s;
    background-color: #fff;
    /* 确保卡片背景为白色 */
    border: 1px solid #eaeaea;
}

.stat-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(160, 120, 85, 0.15);
    /* 更改阴影颜色为棕色系 */
}

.card-content {
    display: flex;
    align-items: center;
    height: 100%;
}

.card-icon {
    font-size: 48px;
    color: #A65E2E;
    /* 更改为棕红色，更符合非遗文化 */
    margin-right: 20px;
}

.card-info {
    flex: 1;
}

.card-title {
    font-size: 14px;
    color: #8C6A4F;
    /* 更改为棕色系 */
    margin-bottom: 10px;
}

.card-value {
    font-size: 24px;
    font-weight: bold;
    color: #5D4037;
    /* 更深的棕色 */
}

/* 图表卡片样式 */
.chart-row {
    margin-bottom: 20px;
}

.chart-card {
    margin-bottom: 20px;
    transition: all 0.3s;
    background-color: #fff;
    border: 1px solid #eaeaea;
}

.chart-card:hover {
    box-shadow: 0 5px 15px rgba(160, 120, 85, 0.15);
    /* 更改阴影颜色为棕色系 */
}

.chart-header {
    display: flex;
    align-items: center;
    color: #8C6A4F;
    /* 更改为棕色系 */
}

.chart-header i {
    margin-left: 8px;
    color: #A65E2E;
    /* 更改为棕红色 */
    cursor: pointer;
}

.echart {
    width: 100%;
}

/* 图标样式 */
.el-icon-collection:before {
    content: "\e79c";
}

.el-icon-s-grid:before {
    content: "\e7a6";
}

.el-icon-location:before {
    content: "\e79e";
}

.el-icon-user:before {
    content: "\e7aa";
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