<template>
    <div>
        <div class="card margin-top-xs" style="display: flex; gap: 20px;">
            <div style="height: 350px;flex:1;">

                <el-carousel :interval="5000" height="350px">
                    <el-carousel-item>
                        <img class="banner-img" :src="require('@/assets/banner05.png')"
                            style="width: 100%;height: 350px;object-fit: cover;" />
                    </el-carousel-item>
                    <el-carousel-item>
                        <img class="banner-img" :src="require('@/assets/banner06.png')"
                            style="width: 100%;height: 350px;object-fit: cover;" />
                    </el-carousel-item>
                </el-carousel>
            </div>
            <div class="notice-container" style="width: 300px;">
                <div class="notice-header">
                    <h3>系统公告</h3>
                    <el-button type="text" @click="viewMore">查看更多</el-button>
                </div>
                <div class="notice-list">
                    <div v-for="item in SysNoticeDataList" :key="item.Id" class="notice-item" @click="ToNoticeDetail(item)">
                        <div class="notice-title">{{ item.Title }}</div>
                        <div class="notice-time">{{ item.CreationTime }}</div>
                    </div>
                </div>
            </div>
        </div>

        <div class="share-section">
            <div class="share-header">
                <div class="share-title">
                    <i class="el-icon-s-opportunity"></i>
                    <h3>网友分享</h3>
                </div>
                <el-button type="text" @click="viewMoreTopics">查看更多</el-button>
            </div>

            <PaginationBox ref="TopicPaginationBox" :where="{}" url="/Topic/RecommendTop10" :limit="10">
                <template v-slot:content="{ data }">
                    <div class="share-content">
                        <div v-for="(item, index) in data" :key="index" class="share-item" @click="toTopicDetail(item)">
                            <div class="share-item-image">
                                <img :src="item.Cover" alt="封面图片">
                            </div>
                            <div class="share-item-info">
                                <div class="share-item-title">{{ item.Title }}</div>
                                <div class="share-item-meta">
                                    <span class="share-item-author">{{ item.CreatorAppUserDto ? item.CreatorAppUserDto.Name
                                        : '匿名用户' }}</span>
                                    <span class="share-item-time">{{ item.CreationTime }}</span>
                                </div>
                                <div class="share-item-type">
                                    <el-tag size="mini" type="info">{{ item.TopicTypeDto ? item.TopicTypeDto.Name : '未分类'
                                    }}</el-tag>
                                </div>
                            </div>
                        </div>
                    </div>
                </template>
            </PaginationBox>
        </div>

    </div>
</template>


<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
export default {
    name: 'Home',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        PaginationBox
    },
    data() {
        return {
            SysNoticeDataList: [],

        }
    },
    created() {
        this.SysNoticeListApi();

    },
    methods: {

        async SysNoticeListApi() {
            let { Data: { Items } } = await this.$Post('/SysNotice/List', { Id: this.$route.query.SysNoticeId });
            this.SysNoticeDataList = Items;
        }, viewMore() {
            this.$router.push('/Front/SysNoticeList');
        },
        async ToNoticeDetail(item) {
            this.$router.push({
                path: '/Front/SysNoticeDetail',
                query: {
                    SysNoticeId: item.Id,
                }
            })
        },
        viewMoreTopics() {
            this.$router.push('/Front/TopicList');
        },
        toTopicDetail(item) {
            this.$router.push({
                path: '/Front/TopicDetail',
                query: {
                    TopicId: item.Id
                }
            });
        }
    },

}
</script>


<style scoped>
.notice-container {
    background: #fff;
    padding: 15px;
    height: 350px;
    overflow: hidden;
}

.notice-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
}

.notice-header h3 {
    margin: 0;
    color: #333;
}

.notice-list {
    overflow-y: auto;
    height: calc(100% - 50px);
}

.notice-item {
    padding: 10px 0;
    border-bottom: 1px solid #f5f5f5;
    cursor: pointer;
}

.notice-item:hover {
    background-color: #f5f5f5;
}

.notice-title {
    font-size: 14px;
    color: #333;
    margin-bottom: 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.notice-time {
    font-size: 12px;
    color: #999;
}

/* 网友分享区域样式 */
.share-section {
    margin-top: 20px;
    background: #fff;
    padding: 15px;
    border-radius: 4px;
}

.share-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
}

.share-title {
    display: flex;
    align-items: center;
}

.share-title i {
    color: var(--primary-color);
    font-size: 20px;
    margin-right: 8px;
}

.share-title h3 {
    margin: 0;
    color: #333;
}

.share-content {
    /* 分享内容容器 */
    display: grid;
    grid-template-columns: repeat(5, minmax(0, 1fr));
    /* 确保每列平均分配空间 */
    gap: 20px;
    width: 100%;
    /* 确保容器占满父元素宽度 */
}

.share-item {
    /* 分享项目卡片 */
    width: 100%;
    /* 确保每个项目占满网格单元格 */
    cursor: pointer;
    transition: all 0.3s;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.share-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.share-item-image {
    height: 160px;
    overflow: hidden;
}

.share-item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.5s;
}

.share-item:hover .share-item-image img {
    transform: scale(1.05);
}

.share-item-info {
    padding: 10px;
}

.share-item-title {
    font-size: 14px;
    font-weight: bold;
    color: #333;
    margin-bottom: 8px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.share-item-meta {
    display: flex;
    justify-content: space-between;
    font-size: 12px;
    color: #999;
    margin-bottom: 8px;
}

.share-item-author {
    max-width: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.share-item-time {
    font-size: 12px;
}

.share-item-type {
    text-align: right;
}
</style>