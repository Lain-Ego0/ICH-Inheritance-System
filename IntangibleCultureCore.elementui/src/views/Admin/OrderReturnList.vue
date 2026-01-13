<template>
    <div class="app-container">



        <el-dialog title="处理退款" :visible.sync="editorShow" width="50%" :lock-scroll="true" height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">
                    <el-col :span="24">
                        <el-form-item label="留证拍照" prop="SellerImageUrls">
                            <UploadImages :limit="6" v-model="formData.SellerImageUrls"></UploadImages>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="回复内容" prop="SellerReply">
                            <el-input type="textarea" v-model="formData.SellerReply" placeholder="请输入回复内容"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="是否同意退款" prop="IsAggren">
                            <el-switch v-model="formData.IsAggren">
                            </el-switch>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="SellerDispatch()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>

        <PaginationTable ref="PaginationTableId" url="/OrderReturn/List" :column="dataColum" :where="where">

            <template v-slot:Operate="scope">
                <el-button type="primary" size="mini" v-if="scope.row.IsAggren == null"
                    @click="ShowEditModal(scope.row.Id)">处 理</el-button>

            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex'
export default {
    name: "OrderReturnList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {

        return {
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
                {
                    key: "OrderId",
                    hidden: true,
                },
                {
                    key: "OrderDto.OrderNo",
                    title: "订单编号",
                    width: "250px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "UserId",
                    hidden: true,
                },
                {
                    key: "OrderReturnTypeFormat",
                    title: "退款方式",
                    width: "250px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "UserDto.Name",
                    title: "买家",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "UserImageUrls",
                    title: "用户拍照",
                    width: "150px",
                    type: store.getters.ColumnType.IMAGES,
                },

                {
                    key: "ShopUserId",
                    hidden: true,
                },

                {
                    key: "ApplyReason",
                    title: "申请原因",
                    width: "350px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "LogisticsNo",
                    title: "物流单号",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },

                {
                    key: "ReturnMoney",
                    title: "申请退款金额",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "SellerReply",
                    title: "卖家回复",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "SellerImageUrls",
                    title: "卖家拍照",
                    width: "150px",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "IsAggren",
                    title: "卖家是否同意",
                    width: "150px",
                    type: store.getters.ColumnType.JUDGMENTTAG,
                }, {
                    key: "IsApplyCustom",
                    title: "是否申请客服",
                    width: "150px",
                    type: store.getters.ColumnType.JUDGMENTTAG,
                },

                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "OrderId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "SellerReply": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ApplyReason": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "IsAggren": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,
            where: {}
        };
    },
    created() {
        this.where.OrderId = this.$route.query.OrderId;
    },
    methods: {
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/OrderReturn/Get`, `/OrderReturn/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/OrderReturn/Get`, { Id: Id });
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async SellerDispatch() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/OrderReturn/SellerDispatch`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
        },





    },
};
</script>

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