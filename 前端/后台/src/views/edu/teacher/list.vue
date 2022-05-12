<template>
  <div class="app-container">
    UP列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="UP昵称" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="分类">
          <el-option :value="1" label="编程" />
          <el-option :value="2" label="其他" />
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >
      <el-button type="default" icon="el-icon-minus" @click="resetData()"
        >清空</el-button
      >
    </el-form>
    <p></p>
    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="昵称" width="120" />

      <el-table-column label="分类" width="80">
        <template slot-scope="scope">
          {{ scope.row.level === 1 ? "编程" : "其他" }}
        </template>
      </el-table-column>

      <el-table-column prop="mid" label="mid" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            icon="el-icon-info"
            @click="getUPInfo(scope.row.mid)"
            >详情</el-button
          >
          <router-link :to="'/teacher/edit/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
              >修改</el-button
            >
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
import teacher from "@/api/edu/teacher";
import teacherInfo from "@/api/bilibili/up";

export default {
  data() {
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
      teacherQuery: {}, //条件封装对象
      info: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //创建具体的方法，调用teacher.js定义的方法
    //讲师列表的方法
    getList(page = 1) {
      this.page = page;
      teacher
        .getTeacherListPage(this.page, this.limit, this.teacherQuery)
        .then((response) => {
          //请求成功
          //response接口返回的数据
          //console.log(response)
          this.list = response.data.rows;
          this.total = response.data.total;
          console.log(this.list);
          console.log(this.total);
        });
    },
    resetData() {
      //清空的方法
      //表单输入项数据清空
      this.teacherQuery = {};
      //查询所有讲师数据
      this.getList();
    },
    //删除讲师的方法
    removeDataById(id) {
      this.$confirm("此操作将永久删除此UP,且无法恢复, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //点击确定，执行then方法
        //调用删除的方法
        teacher.deleteTeacherId(id).then((response) => {
          //删除成功
          //提示信息
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          //回到列表页面
          this.getList();
        });
      }); //点击取消，执行catch方法
    },
    getUPInfo(mid) {
      teacherInfo.getTeacherInfoByID(mid).then((response) => {
        this.showInfo(JSON.parse(response.data.json).data);
      });
    },
    showInfo(info) {
      var str =
        '<head><meta name="referrer" content="no-referrer"/> </head><body>' +
        "昵称：" +
        info.name +
        "<br/>" +
        "mid：" +
        info.mid +
        "<br/>" +
        "性别：" +
        info.sex +
        "<br/>" +
        "当前等级：" +
        info.level +
        "<br/>" +
        "签名：" +
        info.sign +
        "<br/>" +
        "头像：" +
        '<image src="' +
        info.face +
        '" width="100px" height="100px"/></body>';
      this.$alert(str, "", {
        dangerouslyUseHTMLString: true,
      });
    },
  },
};
</script>
