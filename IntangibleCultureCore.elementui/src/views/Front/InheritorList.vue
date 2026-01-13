<template>
    <div>
        <div class="inheritor-header">
            <h1 class="main-title">非物质文化遗产传承人</h1>
            <div class="sub-title">守护传统 · 传承匠心</div>
        </div>

        <!-- 搜索区域 -->
        <div class="search-container">
            <div class="search-box">
                <el-input v-model="searchKeyword" placeholder="请输入关键词搜索传承人" class="search-input"
                    @keyup.enter.native="handleSearch">
                    <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
                </el-input>
            </div>

            <!-- 搜索历史标签 -->
            <div class="search-history" v-if="searchHistory.length > 0">
                <div class="history-header">
                    <span>搜索历史</span>
                    <el-button type="text" @click="clearSearchHistory">清除历史</el-button>
                </div>
                <div class="history-tags">
                    <el-tag v-for="(tag, index) in searchHistory" :key="index" size="small" closable
                        @click="useHistoryTag(tag)" @close="removeHistoryTag(index)">
                        {{ tag }}
                    </el-tag>
                </div>
            </div>
        </div>

        <PaginationBox ref="PaginationBox" :where="where" url="/Inheritor/List" class="pagination-container" :limit="8">
            <template v-slot:content="{ data }">
                <div class="inheritor-grid">
                    <div v-for="(item, index) in data" :key="index" class="inheritor-card" @click="ToDetail(item.Id)">
                        <div class="card-inner">
                            <div class="image-container">
                                <img :src="item.ImageUrls" alt="传承人照片" class="inheritor-image">
                                <div class="inheritor-no">编号: {{ item.No }}</div>
                            </div>
                            <div class="inheritor-info">
                                <div class="inheritor-name">{{ item.Name }}</div>
                                <div class="inheritor-meta">
                                    <span class="meta-item"><i class="el-icon-user"></i> {{ item.Sex }}</span>
                                    <span class="meta-item"><i class="el-icon-location"></i> {{ item.Nation }}</span>
                                </div>
                                <div class="inheritor-location">{{ formatLocation(item.ProviceCityArea) }}</div>
                                <div class="inheritor-content">{{ item.Content }}</div>
                            </div>
                            <div class="view-more">
                                <span>查看详情</span>
                                <i class="el-icon-arrow-right"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </template>
        </PaginationBox>
    </div>
</template>
<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
export default {
    name: 'Inheritor',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'])
    },
    components: {
        PaginationBox,
    },
    data() {
        return {
            where: {
                KeyWord: ''
            },
            searchKeyword: '',
            searchHistory: []
        }
    },
    created() {
        // 从本地存储加载搜索历史
        const savedHistory = localStorage.getItem('inheritorSearchHistory');
        if (savedHistory) {
            this.searchHistory = JSON.parse(savedHistory);
        }
    },
    methods: {
        ToDetail(Id) {
            this.$router.push({
                path: '/Front/InheritorDetail',
                query: {
                    InheritorId: Id,
                }
            })
        },
        formatLocation(location) {
            if (!location) return '';
            return location.split(',').join(' · ');
        },
        // 处理搜索
        handleSearch() {
            this.where.KeyWord = this.searchKeyword;
            this.$refs.PaginationBox.Reload(this.where);

            // 保存非空搜索关键词到历史记录
            if (this.searchKeyword.trim() !== '') {
                this.saveSearchKeyword(this.searchKeyword);
            }
        },
        // 保存搜索关键词到历史
        saveSearchKeyword(keyword) {
            // 如果已存在相同关键词，先移除它
            const index = this.searchHistory.indexOf(keyword);
            if (index !== -1) {
                this.searchHistory.splice(index, 1);
            }

            // 添加到历史记录开头
            this.searchHistory.unshift(keyword);

            // 限制历史记录数量为10个
            if (this.searchHistory.length > 10) {
                this.searchHistory.pop();
            }

            // 保存到本地存储
            localStorage.setItem('inheritorSearchHistory', JSON.stringify(this.searchHistory));
        },
        // 使用历史标签进行搜索
        useHistoryTag(tag) {
            this.searchKeyword = tag;
            this.handleSearch();
        },
        // 移除单个历史标签
        removeHistoryTag(index) {
            this.searchHistory.splice(index, 1);
            localStorage.setItem('inheritorSearchHistory', JSON.stringify(this.searchHistory));
        },
        // 清除所有搜索历史
        clearSearchHistory() {
            this.searchHistory = [];
            localStorage.removeItem('inheritorSearchHistory');
        }
    },
}
</script>
<style scoped>
/* 整体容器样式 */
.pagination-container {
    width: 100%;
}

/* 头部标题样式 */
.inheritor-header {
    text-align: center;
    margin: 30px 0;
    padding: 20px;
    background: linear-gradient(135deg, rgba(157, 41, 51, 0.1) 0%, rgba(179, 92, 68, 0.05) 100%);
    border-radius: 8px;
}

.main-title {
    font-size: 32px;
    color: #333;
    margin-bottom: 10px;
    font-weight: bold;
    letter-spacing: 2px;
}

.sub-title {
    font-size: 16px;
    color: #666;
    letter-spacing: 1px;
}

/* 搜索容器样式 */
.search-container {
    margin: 20px 0 30px;
    background: #f9f9f9;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

/* 搜索框样式 */
.search-box {
    max-width: 600px;
    margin: 0 auto;
}

.search-input {
    width: 100%;
}

.search-input>>>.el-input__inner {
    border-radius: 20px 0 0 20px;
    height: 42px;
    font-size: 15px;
}

.search-input>>>.el-input-group__append {
    border-radius: 0 20px 20px 0;
    background-color: #9D2933;
    border-color: #9D2933;
    color: white;
    padding: 0 15px;
}

.search-input>>>.el-input-group__append .el-icon-search {
    font-size: 18px;
}

/* 搜索历史样式 */
.search-history {
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px dashed #ddd;
}

.history-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    color: #666;
    font-size: 14px;
}

.history-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}

.history-tags>>>.el-tag {
    cursor: pointer;
    transition: all 0.2s;
    background-color: #FDF5E6;
    border-color: #B35C44;
    color: #9D2933;
}

.history-tags>>>.el-tag:hover {
    background-color: #f5e6d3;
    transform: translateY(-2px);
}

/* 传承人网格布局 */
.inheritor-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 25px;
    width: 100%;
    margin: 20px 0;
}

/* 传承人卡片样式 */
.inheritor-card {
    border-radius: 10px;
    overflow: hidden;
    transition: all 0.3s ease;
    cursor: pointer;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
    height: 100%;
    background: #fff;
}

.inheritor-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
}

.card-inner {
    display: flex;
    flex-direction: column;
    height: 100%;
}

/* 图片容器 */
.image-container {
    position: relative;
    height: 200px;
    overflow: hidden;
}

.inheritor-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.5s ease;
}

.inheritor-card:hover .inheritor-image {
    transform: scale(1.05);
}

.inheritor-no {
    position: absolute;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.7);
    color: #fff;
    padding: 5px 10px;
    font-size: 12px;
    border-top-left-radius: 8px;
}

/* 传承人信息 */
.inheritor-info {
    padding: 15px;
    flex-grow: 1;
}

.inheritor-name {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 10px;
    color: #333;
}

.inheritor-meta {
    display: flex;
    margin-bottom: 10px;
}

.meta-item {
    margin-right: 15px;
    font-size: 14px;
    color: #666;
}

.inheritor-location {
    font-size: 14px;
    color: #9D2933;
    margin-bottom: 10px;
}

.inheritor-content {
    font-size: 14px;
    color: #555;
    line-height: 1.5;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

/* 查看更多按钮 */
.view-more {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    padding: 10px 15px;
    color: #9D2933;
    font-size: 14px;
    background: #FDF5E6;
    border-top: 1px solid #eee;
}

.view-more i {
    margin-left: 5px;
    transition: transform 0.3s ease;
}

.inheritor-card:hover .view-more i {
    transform: translateX(3px);
}
</style>