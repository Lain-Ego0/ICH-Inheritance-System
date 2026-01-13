<template>
    <div v-if="Cultural">
        <div class="detail-container">
            <!-- 头部信息区域 -->
            <div class="header-section">
                <div class="main-info">
                    <div class="title-wrapper">
                        <h1>{{ Cultural.Name }}</h1>
                        <div class="type-tag">{{ Cultural.CulturalTypeDto.Name }}</div>
                    </div>
                    <div class="no-badge">编号：{{ Cultural.No }}</div>
                </div>

                <div class="meta-info">
                    <div class="info-item">
                        <i class="el-icon-location"></i>
                        <span class="label">申报地区：</span>
                        <span>{{ Cultural.PlaceDeclaration }}</span>
                    </div>
                    <div class="info-item">
                        <i class="el-icon-office-building"></i>
                        <span class="label">保护单位：</span>
                        <span>{{ Cultural.ProtectionUnit }}</span>
                    </div>
                    <div class="info-item">
                        <i class="el-icon-collection-tag"></i>
                        <span class="label">所属批次：</span>
                        <span>{{ Cultural.BatchNo }}</span>
                    </div>
                    <div class="info-item">
                        <i class="el-icon-time"></i>
                        <span class="label">记录时间：</span>
                        <span>{{ Cultural.RecordDate }}</span>
                    </div>
                </div>
            </div>

            <!-- 主要内容区域 -->
            <div class="content-section">
                <div class="image-showcase">
                    <img :src="Cultural.ImageUrls" :alt="Cultural.Name">
                    <div class="image-overlay"></div>
                </div>

                <div class="description-section">
                    <h2><i class="el-icon-document"></i>文化简介</h2>
                    <div class="content-text" v-html="Cultural.Content"></div>
                </div>

                <!-- 传承人信息卡片 -->
                <div class="inheritor-section" v-if="Cultural.InheritorDto">
                    <h2><i class="el-icon-user"></i>传承人信息</h2>
                    <div class="inheritor-card">
                        <div class="inheritor-header">
                            <div class="inheritor-avatar">
                                <img :src="Cultural.InheritorDto.ImageUrls || '/default-avatar.png'"
                                    :alt="Cultural.InheritorDto.Name">
                            </div>
                            <div class="inheritor-title">
                                <h3>{{ Cultural.InheritorDto.Name }}</h3>
                                <div class="inheritor-no">{{ Cultural.InheritorDto.No }}</div>
                            </div>
                        </div>

                        <div class="inheritor-details">
                            <div class="detail-item">
                                <i class="el-icon-user"></i>
                                <span>{{ Cultural.InheritorDto.Nation }} | {{ Cultural.InheritorDto.Sex }}</span>
                            </div>
                            <div class="detail-item">
                                <i class="el-icon-date"></i>
                                <span>{{ Cultural.InheritorDto.BirthDate }}</span>
                            </div>
                            <div class="detail-item">
                                <i class="el-icon-location-information"></i>
                                <span>{{ Cultural.InheritorDto.ProviceCityArea }}</span>
                            </div>
                        </div>

                        <div class="inheritor-description" v-html="Cultural.InheritorDto.Content"></div>
                    </div>
                </div>
            </div>

            <!-- 推荐非遗文化区域 -->
            <div class="recommend-section">
                <h2 class="section-title"><i class="el-icon-connection"></i>相关推荐</h2>
                <div class="recommend-list">
                    <PaginationBox ref="RecommendByContentPaginationBox" :where="{ Id: Cultural.Id }"
                        url="/Cultural/RecommendByContent" :limit="8">
                        <template v-slot:content="{ data }">
                            <div class="recommend-grid">
                                <div v-for="(item, index) in data" :key="index" class="recommend-item"
                                    @click="goToDetail(item.Id)">
                                    <div class="recommend-image">
                                        <img :src="item.ImageUrls" :alt="item.Name">
                                    </div>
                                    <div class="recommend-info">
                                        <h3 class="recommend-title">{{ item.Name }}</h3>
                                        <div class="recommend-meta">
                                            <span class="recommend-type">{{ item.CulturalTypeDto.Name }}</span>
                                            <span class="recommend-place">{{ item.PlaceDeclaration }}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </template>
                    </PaginationBox>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import SelectTab from '@/components/Select/SelectTab.vue';
import { mapGetters } from 'vuex';
export default {
    name: 'Cultural',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'
        ])
    },
    components: {
        PaginationBox,
        SelectTab
    },
    data() {
        return {
            Cultural: null,
        }
    },
    watch: {
        "$route": async function (to, from) {
            await this.GeCulturalApi();
        }
    },
    created() {
        this.GetCulturalApi();
    },
    methods: {
        //得到数据接口
        async GetCulturalApi() {
            let { Data
            } = await this.$Post("/Cultural/Get",
                {
                    Id: this.$route.query.CulturalId
                })
            this.Cultural = Data;
            this.$nextTick(() => { })
        },

        // 跳转到详情页
        goToDetail(id) {
            this.$router.push({
                path: '/Front/CulturalDetail',
                query: { CulturalId: id }
            });
        },
    },
}
</script>
<style scoped>
/* 面包屑导航容器 */
.breadcrumb-container {
    padding: 15px 0;
}

.breadcrumb-container .location-label {
    font-weight: 600;
    margin-right: 10px;
    color: #606266;
}

/* 详情页主容器 */
.detail-container {
    margin-top: 10px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    overflow: hidden;
}

/* 头部区域样式 */
.header-section {
    padding: 30px;
    background: linear-gradient(135deg, #f6f8fc 0%, #f0f4f9 100%);
}

.header-section .main-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 25px;
}

.header-section .main-info .title-wrapper {
    display: flex;
    align-items: center;
    gap: 15px;
}

.header-section .main-info .title-wrapper h1 {
    font-size: 32px;
    margin: 0;
    color: #2c3e50;
}

.header-section .main-info .title-wrapper .type-tag {
    background: var(--primary-color);
    color: white;
    padding: 4px 12px;
    border-radius: 20px;
    font-size: 14px;
}

.header-section .main-info .no-badge {
    background: rgba(64, 158, 255, 0.1);
    color: var(--primary-color);
    padding: 6px 15px;
    border-radius: 20px;
    font-size: 14px;
}

/* 元信息区域 */
.header-section .meta-info {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
}

.header-section .meta-info .info-item {
    display: flex;
    align-items: center;
    color: #606266;
}

.header-section .meta-info .info-item i {
    margin-right: 8px;
    color: var(--primary-color);
}

.header-section .meta-info .info-item .label {
    color: #909399;
    margin-right: 8px;
}

/* 内容区域样式 */
.content-section {
    padding: 30px;
}

.content-section .image-showcase {
    position: relative;
    margin-bottom: 40px;
    border-radius: 12px;
    overflow: hidden;
}

.content-section .image-showcase img {
    width: 100%;
    height: 400px;
    object-fit: cover;
    display: block;
}

.content-section .image-showcase .image-overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100px;
    background: linear-gradient(to bottom, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.1));
}

.content-section h2 {
    font-size: 24px;
    color: #2c3e50;
    margin-bottom: 20px;
    display: flex;
    align-items: center;
}

.content-section h2 i {
    margin-right: 10px;
    color: var(--primary-color);
}

.content-section .content-text {
    line-height: 1.8;
    color: #606266;
    font-size: 16px;
}

/* 传承人信息区域 */
.inheritor-section {
    margin-top: 40px;
}

.inheritor-section .inheritor-card {
    background: #f8fafc;
    border-radius: 12px;
    padding: 25px;
}

.inheritor-section .inheritor-card .inheritor-header {
    display: flex;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;
}

.inheritor-section .inheritor-card .inheritor-header .inheritor-avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    overflow: hidden;
    border: 3px solid #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.inheritor-section .inheritor-card .inheritor-header .inheritor-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.inheritor-section .inheritor-card .inheritor-header .inheritor-title {
    flex: 1;
}

.inheritor-section .inheritor-card .inheritor-header .inheritor-title h3 {
    font-size: 22px;
    color: #2c3e50;
    margin: 0 0 5px 0;
}

.inheritor-section .inheritor-card .inheritor-header .inheritor-title .inheritor-no {
    color: #909399;
    font-size: 14px;
}

.inheritor-section .inheritor-card .inheritor-details {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 15px;
    margin-bottom: 20px;
}

.inheritor-section .inheritor-card .inheritor-details .detail-item {
    display: flex;
    align-items: center;
    color: #606266;
}

.inheritor-section .inheritor-card .inheritor-details .detail-item i {
    color: var(--primary-color);
    margin-right: 8px;
}

.inheritor-section .inheritor-card .inheritor-description {
    color: #606266;
    line-height: 1.8;
    font-size: 15px;
    padding-top: 20px;
    border-top: 1px solid #ebeef5;
}

/* 推荐非遗文化区域 */
.recommend-section {
    padding: 30px;
    margin-top: 40px;

    border-top: 1px solid #ebeef5;
}

.recommend-section .section-title {
    font-size: 24px;
    color: #2c3e50;
    margin-bottom: 25px;
    display: flex;
    align-items: center;
}

.recommend-section .section-title i {
    margin-right: 10px;
    color: var(--primary-color);
}

.recommend-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
}

.recommend-item {
    background: #fff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
    cursor: pointer;
}

.recommend-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.recommend-image {
    height: 160px;
    overflow: hidden;
}

.recommend-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.5s ease;
}

.recommend-item:hover .recommend-image img {
    transform: scale(1.05);
}

.recommend-info {
    padding: 15px;
}

.recommend-title {
    font-size: 16px;
    color: #2c3e50;
    margin: 0 0 8px 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.recommend-meta {
    display: flex;
    justify-content: space-between;
    font-size: 13px;
    color: #909399;
}

.recommend-type {
    background: rgba(64, 158, 255, 0.1);
    color: var(--primary-color);
    padding: 2px 8px;
    border-radius: 10px;
}

.recommend-place {
    color: #606266;
}
</style>