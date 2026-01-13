<template>
    <div>
        <div>
            <h2 style="font-size: 16px;color: var(--primary-color);">文创品</h2>
        </div>
        <div style="display: flex;margin-top:15px">
            <div class="good-type-list">
                <div v-for="(item, index) in GoodTypeList" :key="item.Id" @click="ToType(item)">
                    <div style="margin-bottom:5px">
                        <span class="catory-item">{{ item.Name }}</span>
                    </div>

                </div>
            </div>
            <div style="height: 500px;flex:1">
                <el-carousel :interval="5000" height="500px">
                    <el-carousel-item>
                        <img class="banner-img" :src="require('@/assets/banner07.png')"
                            style="width: 100%;height: 500px;object-fit: cover;">
                    </el-carousel-item>
                    <el-carousel-item>
                        <img class="banner-img" :src="require('@/assets/banner08.png')"
                            style="width: 100%;height: 500px;object-fit: cover;">
                    </el-carousel-item>

                </el-carousel>
            </div>



        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: "Home",
    components: {

    },
    computed: {
        ...mapGetters(["UserInfo", "Token"])
    },
    data() {
        return {

            SysNoticeList: [],
            classOption: {
                direction: 0,
                step: 0.25,
                singleHeight: 20,
                limitMoveNum: 1
            },
            GoodTypeList: [],

        }
    },
    created() {
        this.GetGoodTypeListApi()
        this.GetSysNoticeListApi();

    },
    methods: {


        async GetSysNoticeListApi() {
            let { Data: { TotalCount, Items } } = await this.$Post("/SysNotice/List", {})
            this.SysNoticeList = Items;
        },
        async GetGoodTypeListApi() {
            let { Data: { TotalCount, Items } } = await this.$Post("/GoodType/List", { IsShop: false })
            this.GoodTypeList = Items;
            console.log(Items);

        },
        //前往我收藏的文创品
        async ToGoodCollect() {
            this.$router.push({
                path: "/Front/GoodCollect"
            })
        },
        //前往我收藏的店铺
        async ToShopCollect() {
            this.$router.push({
                path: "/Front/ShopCollect"
            })

        },
        //前往分类
        async ToType(item) {
            this.$router.push({
                path: "/Front/GoodTypeList",
                query: {
                    GoodTypeId: item.Id,
                    GoodTypeName: item.Name
                }
            })
        },
        //公告
        async ToDetail(item) {
            this.$router.push({
                path: "/Front/SysNotice",
                query: {
                    Id: item.Id
                }
            })
        }
    }

}
</script>


<style scope>
.good-type-list {
    background-color: #F3F6F8;
    border-radius: 10px;
    width: 180px;
    margin-right: 20px;

    padding: 10px;
}

.catory-item {
    color: #666;
    font-size: 15px;
    cursor: pointer;
}

.catory-item:hover {
    color: var(--primary-color);
}

.banner-img {
    border-radius: 20px;
    width: 510px;
    height: 280px;
    overflow: hidden;
}

.banner2-box {
    display: flex;
    justify-items: center;
    justify-content: space-between
}

.banner2-box img {
    width: 246px;
    height: 206px;
    border-radius: 12px;
}
</style>