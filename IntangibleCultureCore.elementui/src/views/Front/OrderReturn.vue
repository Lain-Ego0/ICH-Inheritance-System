<template>
    <div>
        <div class="page-container margin-top-xs card" v-if="OrderInfo">
            <el-page-header @back="goBack()" content="退款售后">
            </el-page-header>

            <div class="margin-top-xs">

                <div class="order-box" v-if="OrderInfo != null">
                    <div class="order-no">订单号:{{ OrderInfo.OrderNo }}</div>
                    <div class="shop margin-top-xs">
                        <img :src="OrderInfo.ShopDto.LogoCover" class="shop-logo">
                        <div>店铺:<span>{{ OrderInfo.ShopDto.Name }}</span></div>

                    </div>
                    <el-table class="margin-top-xs" :data="OrderInfo.OrderDets" stripe style="width: 100%">

                        <el-table-column label="文创品图片" width="100" :align="'center'">
                            <template slot-scope="scope">
                                <img :src="scope.row.GoodDto.Cover" class="good-img" />
                            </template>
                        </el-table-column>
                        <el-table-column label="文创品名称" :align="'center'">
                            <template slot-scope="scope">
                                <span>{{ scope.row.GoodDto.Name }}</span>
                            </template>
                        </el-table-column>


                        <el-table-column label="单价" width="180" :align="'center'">
                            <template slot-scope="scope">
                                <span class="price">￥{{ scope.row.GoodDto.Price }}</span>
                            </template>
                        </el-table-column>

                        <el-table-column prop="address" label="小计" width="180" :align="'center'">
                            <template slot-scope="scope">
                                <span class="price"> {{ scope.row.Qty * scope.row.GoodDto.Price | toFixed2 }}</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <h2 class="margin-top-xs">填写申请表单</h2>
                <el-form ref="editModalForm" class="margin-top-xs" :rules="editModalFormRules" :model="formData"
                    label-width="140px" size="mini">
                    <el-row :gutter="10" class="EditFromBody">

                        <el-col :span="24">
                            <el-form-item label="申请类型" prop="OrderReturnType">
                                <SigleSelect url="/Select/OrderReturnType" columnName="Name" :clearable="true"
                                    columnValue="Code" v-model="formData.OrderReturnType">
                                </SigleSelect>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="用户拍照" prop="ImageUrls">
                                <UploadImages :limit="6" v-model="formData.ImageUrls"></UploadImages>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24" v-if="['1', '2'].indexOf(formData.OrderReturnType) != -1">
                            <el-form-item label="物流单号" prop="LogisticsNo">
                                <el-input type="text" v-model.trim="formData.LogisticsNo" placeholder="请输入物流单号"
                                    :clearable="true"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="申请原因" prop="ApplyReason">
                                <el-input type="textarea" v-model.trim="formData.ApplyReason" placeholder="请输入申请原因"
                                    :clearable="true" :rows="5" :maxlength="200" show-word-limit></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24" v-if="['0', '1'].indexOf(formData.OrderReturnType) != -1">
                            <el-form-item label="申请退款金额" prop="ReturnMoney">
                                <el-input-number v-model="formData.ReturnMoney" @change="handleChange" :min="9"
                                    :max="OrderInfo.TotalMoney" label="请输入退款金额"></el-input-number>

                            </el-form-item>
                        </el-col>
                    </el-row>


                </el-form>
                <template v-if="formData.IsAggren != null">
                    <h2 class="margin-top-xs">商家回复</h2>
                    <el-form class="margin-top-xs" label-width="140px" size="mini" :disabled="true">
                        <el-row :gutter="10" class="EditFromBody">


                            <el-col :span="24">
                                <el-form-item label="卖家举证" prop="SellerImageUrls">
                                    <UploadImages :limit="6" v-model="formData.SellerImageUrls"></UploadImages>
                                </el-form-item>
                            </el-col>


                            <el-col :span="24">
                                <el-form-item label="卖家回复" prop="SellerReply">
                                    <el-input type="textarea" v-model.trim="formData.SellerReply" placeholder="请输入申请原因"
                                        :clearable="true" :rows="5" :maxlength="200" show-word-limit></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="是否同意退款" prop="IsAggren">
                                    <span style="color:red;font-size: 20px">{{ formData.IsAggren ? "同意" : "商家不同意"
                                    }}</span>
                                </el-form-item>
                            </el-col>
                        </el-row>


                    </el-form>
                </template>
            </div>
            <div class="footer margin-top-xs">
                <template v-if="formData.IsAggren == null">
                    <div class="btn" v-if="formData.IsEdit == false" @click="OrderReturn()">提 交 申 请 </div>
                    <div class="btn" v-if="formData.IsEdit == true" @click="OrderReturn()">修 改 提 交 </div>
                    <div class="btn btn-discard" v-if="formData.IsEdit == true" @click="DiscradBtn()">撤 销 申 请 </div>
                </template>
                <template v-else>
                    <div class="btn" v-if="formData.IsEdit == true" @click="ResetBtn()">再次提交</div>
                    <div class="btn btn-discard" v-if="formData.IsEdit == true" @click="DiscradBtn()">撤 销 申 请 </div>
                </template>
            </div>


        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: "OrderInfoList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {

        return {
            OrderInfo: null,
            editModalFormRules: {
                "OrderReturnType": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "LogisticsNo": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ShopUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ApplyReason": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReturnMoney": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象

        };
    },
    async created() {
        await this.GetOrderInfoApi();
        await this.GetOrderReturnInfoApi();
    },
    methods: {
        //查看订单详情
        async GetOrderInfoApi() {
            let { Data } = await this.$Post("/OrderInfo/Get", { Id: this.$route.query.OrderInfoId })

            this.OrderInfo = Data;

        },
        //查询是否有退款记录
        async GetOrderReturnInfoApi() {
            let { Data } = await this.$Post("/OrderReturn/Get", { Id: 0, OrderId: this.$route.query.OrderInfoId, IsDiscard: false })
            //说明是第一次进行申请
            Data.IsEdit = true;
            if (!Data.Id) {
                Data.IsEdit = false;
                Data.OrderId = this.OrderInfo.Id;
                Data.UserId = this.OrderInfo.UserId;
                Data.ShopUserId = this.OrderInfo.ShopUserId;
                Data.IsDiscard = false;
                Data.ReturnMoney = this.OrderInfo.TotalMoney;
            }
            this.formData = Data;
        },
        //撤销本次申请
        async DiscradBtn() {
            this.formData.IsDiscard = true;
            let { Success } = await this.$Post("/OrderReturn/Discard", this.formData);
            if (Success) {
                this.$message.success("撤销成功,即将返回上一个页面");
                this.$router.go(-1);
            }
        },
        //再次提交
        async ResetBtn() {
            this.formData.IsDiscard = true;
            let { Success } = await this.$Post("/OrderReturn/Discard", this.formData);
            this.GetOrderReturnInfoApi();
        },
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        },
        async OrderReturn() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    let { Success } = await this.$Post("/OrderReturn/CreateOrEdit", this.formData)
                    if (Success) {
                        this.$message.success("申请成功,即将返回上一个页面");
                        this.$router.go(-1);
                    }
                }
            }
            )
        }



    },
};
</script>
<style scoped>
.order-box {
    margin-top: 20px;
}

.order-box .order-item {
    margin-top: 15px;
}

.order-box .order-no {
    font-weight: bolder;
}

.order-box .shop {

    background-color: #F3F6F8;
    border-bottom: 1px solid #F3F6F8;
    overflow: hidden;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #11192D;
    border-radius: 20px;
    padding: 10px;
    font-weight: bold;
}

.order-box .shop-logo {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
    object-fit: fill;
}



.order-box .good-img {
    width: 70px;
    height: 70px;
    object-fit: fill;
    border-radius: 10px;
}

.order-box .price {
    color: #FF5000;
    font-weight: 700;
    font-size: 20px;
}

.order-box .order-remark {
    display: flex;
    margin-top: 10px;

}

.order-box .order-remark .tit {
    width: 150px;
    font-size: 16px;
}

.footer {
    display: flex;
    flex-direction: row-reverse;

}

.footer .btn {
    display: inline-block;
    height: 48px;
    width: 120px;
    background-color: #ff5000;
    border-radius: 8px;
    font-family: PingFangSC;
    font-weight: 600;
    font-size: 16px;
    color: #fff;
    text-align: center;
    line-height: 48px;
    text-decoration: none;
    cursor: pointer;
}

.footer .btn-discard {
    background-color: rgb(25, 141, 14);
    margin-right: 20px;
}
</style>