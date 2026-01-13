<template>
    <div>
        <!-- 搜索区域 -->
        <div class="search-container">
            <div class="search-box">
                <el-input v-model="searchKeyword" placeholder="请输入关键词搜索" @keyup.enter.native="handleSearch" clearable
                    @clear="handleSearch">
                    <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
                </el-input>
            </div>
            <!-- 搜索历史标签 -->
            <div class="search-history" v-if="searchHistory.length">
                <span class="history-label">搜索历史：</span>
                <div class="history-tags">
                    <el-tag v-for="(tag, index) in searchHistory" :key="index" closable size="small"
                        @click="handleTagClick(tag)" @close="handleTagClose(index)">
                        {{ tag }}
                    </el-tag>
                    <el-button type="text" size="small" @click="clearHistory" class="clear-btn">
                        清空历史
                    </el-button>
                </div>
            </div>
        </div>

        <!-- 添加分类标签页 -->
        <div class="cultural-tabs">
            <!-- 添加"全部"选项 -->
            <div class="tab-item" :class="{ active: currentType === null }" @click="handleTypeChange(null)">
                全部
            </div>
            <div v-for="item in CulturalTypeDataList" :key="item.Id"
                :class="{ 'tab-item': true, active: currentType === item.Id }" @click="handleTypeChange(item.Id)">
                {{ item.Name }}
            </div>
        </div>

        <PaginationBox ref="PaginationBox" :where="where" url="/Cultural/List">
            <template v-slot:content="{ data }">
                <div class="cultural-grid">
                    <div v-for="item in data" :key="item.Id" class="cultural-item" @click="ToDetail(item.Id)">
                        <div class="cultural-image">
                            <img :src="item.ImageUrls" :alt="item.Name">
                        </div>
                        <div class="cultural-info">
                            <h3>{{ item.Name }}</h3>
                            <div class="info-row">
                                <span class="label">编号:</span>
                                <span>{{ item.No }}</span>
                            </div>
                            <div class="info-row">
                                <span class="label">类型:</span>
                                <span>{{ item.CulturalTypeDto.Name }}</span>
                            </div>
                            <div class="info-row">
                                <span class="label">申报地:</span>
                                <span>{{ item.PlaceDeclaration }}</span>
                            </div>
                            <div class="info-row">
                                <span class="label">批次:</span>
                                <span>{{ item.BatchNo }}</span>
                            </div>
                            <p class="content-preview">{{ item.Content }}</p>
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
    name: 'Cultural',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'])
    },
    components: {
        PaginationBox,
    },
    data() {
        return {
            where: {},
            CulturalTypeDataList: [],
            currentType: null, // 添加当前选中的分类ID
            searchKeyword: '',
            searchHistory: JSON.parse(localStorage.getItem('culturalSearchHistory') || '[]'),
        }
    },
    created() {
        this.CulturalTypeListApi();
    },
    methods: {
        async CulturalTypeListApi() {
            let { Data: { Items } } = await this.$Post('/CulturalType/List', { Id: this.$route.query.CulturalTypeId });
            this.CulturalTypeDataList = Items;
            // 设置默认选中的分类，如果没有指定则选择"全部"
            this.currentType = this.$route.query.CulturalTypeId || null;
            this.where = this.currentType ? { CulturalTypeId: this.currentType } : {};
        },
        handleTypeChange(typeId) {
            this.currentType = typeId;
            // 当选择"全部"时，不传入 CulturalTypeId
            this.where = typeId ? { CulturalTypeId: typeId } : {};
            this.$refs.PaginationBox.Reload(this.where);
        },
        ToDetail(Id) {
            this.$router.push({
                path: '/Front/CulturalDetail',
                query: {
                    CulturalId: Id,
                }
            })
        },
        handleSearch() {
            this.where = {
                ...this.where,
                KeyWord: this.searchKeyword
            };
            this.$refs.PaginationBox.Reload(this.where);

            // 保存搜索历史
            if (this.searchKeyword.trim()) {
                const history = new Set(this.searchHistory);
                history.add(this.searchKeyword);
                this.searchHistory = Array.from(history).slice(-10); // 只保留最近10条
                localStorage.setItem('culturalSearchHistory', JSON.stringify(this.searchHistory));
            }
        },
        handleTagClick(tag) {
            this.searchKeyword = tag;
            this.handleSearch();
        },
        handleTagClose(index) {
            this.searchHistory.splice(index, 1);
            localStorage.setItem('culturalSearchHistory', JSON.stringify(this.searchHistory));
        },
        clearHistory() {
            this.searchHistory = [];
            localStorage.setItem('culturalSearchHistory', JSON.stringify(this.searchHistory));
        },
    },
}
</script>
<style scoped>
.cultural-grid {
    /* 网格布局容器 */
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
    padding: 20px 0;
    width: 100%;
}

.cultural-item {
    /* 单个非遗项目卡片 */
    background: #fff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s;
    cursor: pointer;
}

.cultural-item:hover {
    transform: translateY(-5px);
}

.cultural-image {
    /* 图片容器 */
    height: 200px;
    overflow: hidden;
}

.cultural-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.cultural-info {
    /* 信息内容区 */
    padding: 15px;
}

.cultural-info h3 {
    /* 标题样式 */
    margin: 0 0 10px;
    font-size: 18px;
    color: #333;
}

.info-row {
    /* 信息行样式 */
    margin: 5px 0;
    font-size: 14px;
    color: #666;
}

.info-row .label {
    /* 标签样式 */
    color: #999;
    margin-right: 5px;
}

.content-preview {
    /* 内容预览样式 */
    margin: 10px 0 0;
    font-size: 14px;
    color: #666;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

/* 分类标签页样式 */
.cultural-tabs {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin: 20px 0;
}

.tab-item {
    padding: 8px 16px;
    border-radius: 4px;
    background-color: #f5f5f5;
    color: #666;
    cursor: pointer;
    transition: all 0.3s;
}

.tab-item:hover {
    background-color: #e0e0e0;
}

.tab-item.active {
    background-color: var(--primary-color);
    color: #fff;
}

/* 搜索区域样式 */
.search-container {
    margin: 20px 0;
    padding: 15px;
    background: #f8f9fa;
    border-radius: 8px;
}

.search-box {
    max-width: 500px;
    margin-bottom: 10px;
}

.search-history {
    display: flex;
    align-items: center;
    margin-top: 10px;
}

.history-label {
    color: #606266;
    margin-right: 10px;
    font-size: 14px;
}

.history-tags {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 8px;
}

.history-tags .el-tag {
    cursor: pointer;
}

.clear-btn {
    color: #909399;
    margin-left: 10px;
}

.clear-btn:hover {
    color: var(--primary-color);
}
</style>