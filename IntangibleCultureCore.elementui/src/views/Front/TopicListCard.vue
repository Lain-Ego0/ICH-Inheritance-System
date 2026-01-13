<template>
    <div class="topic-list">
        <div class="topic-card" v-for="(item, index) in TopicList" :key="item.Id" @click="TopicClick(item)">
            <!-- 左侧内容区 -->
            <div class="topic-content">
                <h2 class="topic-title">{{ item.Title }}</h2>

                <div class="topic-author" v-if="item.CreatorAppUserDto">
                    <img class="author-avatar" :src="item.CreatorAppUserDto.ImageUrls || require('@/assets/默认头像.png')">
                    <span class="author-name">{{ item.CreatorAppUserDto.Name }}</span>
                    <span class="publish-time">{{ item.CreationTime }}</span>
                </div>

                <div class="topic-brief">{{ item.Content | RepalceHtml }}</div>

                <div class="topic-stats">
                    <div class="stat-item">
                        <i class="el-icon-view"></i>
                        <span>{{ item.ViewCount || 0 }}</span>
                    </div>
                    <div class="stat-item">
                        <i class="el-icon-chat-dot-round"></i>
                        <span>{{ item.CommentDtos.length }}</span>
                    </div>
                </div>
            </div>

            <!-- 右侧封面图 -->
            <div class="topic-cover" v-if="item.Cover">
                <el-image :src="item.Cover" fit="cover"></el-image>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "TopicListCard",
    props: {
        DataList: {
            type: [Array],
            default: () => { return [] }
        },
    },
    data() {
        return {
            TopicList: []
        }
    },
    created() {

        this.TopicList = this.DataList;
    },
    methods: {
        async TopicClick(item) {
            this.$router.push({
                path: '/Front/TopicDetail',
                query: {
                    TopicId: item.Id,

                }
            })
        }
    }

}
</script>

<style lang="scss" scoped>
.topic-list {
    width: 100%;

    .topic-card {

        display: flex;
        padding: 20px;
        margin-bottom: 16px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
        transition: all 0.3s ease;
        cursor: pointer;

        &:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
        }
    }

    .topic-content {
        flex: 1;
        margin-right: 20px;
    }

    .topic-title {
        font-size: 20px;
        font-weight: 600;
        color: #2c3e50;
        margin-bottom: 12px;
        line-height: 1.4;
    }

    .topic-author {
        display: flex;
        align-items: center;
        margin-bottom: 12px;

        .author-avatar {
            width: 28px;
            height: 28px;
            border-radius: 50%;
            object-fit: cover;
        }

        .author-name {
            margin: 0 12px;
            font-weight: 500;
            color: #606266;
        }

        .publish-time {
            color: #909399;
            font-size: 13px;
        }
    }

    .topic-brief {
        font-size: 15px;
        color: #606266;
        line-height: 1.6;
        margin-bottom: 16px;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }

    .topic-stats {
        display: flex;
        align-items: center;

        .stat-item {
            display: flex;
            align-items: center;
            margin-right: 20px;
            color: #909399;

            i {
                margin-right: 4px;
            }
        }
    }

    .topic-cover {
        width: 180px;
        height: 120px;
        border-radius: 8px;
        overflow: hidden;

        .el-image {
            width: 100%;
            height: 100%;
        }
    }
}
</style>