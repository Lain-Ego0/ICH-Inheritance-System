<template>
    <div>
        <div class="page-container margin-top-xs">
            <div class="card">
                <el-page-header @back="goBack()" content="收藏的文创品">
                </el-page-header>
            </div>

            <div class="card margin-top-xs">
                <div class="good-collect-container">
                    <el-row :gutter="20">
                        <el-col :span="8" v-for="item in collectList" :key="item.Id">
                            <el-card class="good-card" :body-style="{ padding: '0px' }">
                                <img :src="item.GoodDto.Cover" class="good-image">
                                <div class="good-info">
                                    <h3 class="good-name">{{ item.GoodDto.Name }}</h3>
                                    <div class="good-price">￥{{ item.GoodDto.Price }}</div>
                                    <div class="good-buttons">
                                        <el-button type="primary" size="small" @click="LookBtn(item)">查看详情</el-button>
                                        <el-button type="danger" size="small"
                                            @click="cancelCollect(item.Id)">取消收藏</el-button>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </div>
            </div>

            <div class="card margin-top-xs">
                <div class="header-title">猜你喜欢</div>
                <PaginationBox ref="RecommendPaginationBox" url="/Good/GetRecommendList" :where="{}">
                    <template v-slot:content="{ data }">
                        <GoodListCard :DataList="data"></GoodListCard>
                    </template>
                </PaginationBox>
            </div>
        </div>
    </div>
</template>

<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
import GoodListCard from "./GoodListCard.vue";

export default {
    name: "GoodCollectList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        PaginationBox,
        GoodListCard
    },
    data() {
        return {
            collectList: []
        };
    },
    created() {
        this.getCollectList();
    },
    methods: {
        async getCollectList() {
            try {
                const { Data } = await this.$Post('/GoodCollect/List', {
                    CollectUserId: this.UserId
                });
                this.collectList = Data.Items;
            } catch (error) {
                console.error('获取收藏列表失败:', error);
            }
        },
        LookBtn(item) {
            this.$router.push({
                path: '/Front/Good',
                query: {
                    GoodId: item.GoodId
                }
            })
        },
        async cancelCollect(id) {
            await this.$PostDelete('/GoodCollect/Delete', { Id: id });
            this.getCollectList();
        },
        goBack() {
            this.$router.go(-1);
        }
    }
};
</script>

<style scoped>
.good-collect-container {
    padding: 20px;
}

.good-card {
    margin-bottom: 20px;
    transition: all 0.3s;
}

.good-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
}

.good-image {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.good-info {
    padding: 14px;
}

.good-name {
    font-size: 16px;
    margin: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.good-price {
    color: #f56c6c;
    font-size: 18px;
    font-weight: bold;
    margin: 10px 0;
}

.good-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
}
</style>