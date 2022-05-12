<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="分类名称">
        <el-input v-model="subject.title" />
      </el-form-item>
      <el-form-item label="分类等级">
        <el-select v-model="level" clearable placeholder="请选择">
          <el-option :value="1" label="一级分类" />
          <el-option :value="2" label="二级分类" />
        </el-select>
      </el-form-item>
      <el-form-item label="父类" v-show="show_parent">
        <el-select v-model="subject.parentId" clearable placeholder="请选择">
          <el-option
            v-for="item in data2"
            :key="item.id"
            :label="item.title"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveSubject"
          >添加</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import subjectApi from "@/api/edu/subject";
export default {
  data() {
    return {
      // BASE_API: process.env.BASE_API, // 接口API地址
      // importBtnDisabled: false, // 按钮是否禁用,
      // loading: false,
      level: 1,
      show_parent: false,
      data2: {},
      subject: {
        title: "",
        parentId: "0",
        sort: 0,
      },
    };
  },
  created() {
    //页面渲染之前执行
    this.init();
    this.is_show_parent(this.level);
    this.getAllSubjectList();
    console.log(data2);
  },
  watch: {
    //监听
    $route(to, from) {
      //路由变化方式，路由发生变化，方法就会执行
      this.init();
    },
    level: function () {
      this.is_show_parent(this.level);
    },
  },
  methods: {
    is_show_parent(level) {
      if (level == 1) {
        this.show_parent = false;
      } else {
        this.show_parent = true;
      }
    },
    init() {
      //判断路径有id值,做修改
      if (this.$route.params && this.$route.params.id) {
        //从路径获取id值
        const id = this.$route.params.id;
        //调用根据id查询的方法
        this.getInfo(id);
      } else {
        //路径没有id值，做添加
        //清空表单
        this.subject = {};
      }
    },
    getAllSubjectList() {
      subjectApi.getSubjectList().then((response) => {
        this.data2 = response.data.list;
      });
    },
    //添加课程的方法
    saveSubject() {
      subjectApi.addSubject(this.subject).then((response) => {
        //添加成功
        //提示信息
        this.$message({
          type: "success",
          message: "添加成功!",
        });
        //回到列表页面 路由跳转
        this.$router.push({ path: "/subject/list" });
      });
    },
    // //点击按钮上传文件到接口里面
    // submitUpload() {
    //   this.importBtnDisabled = true;
    //   this.loading = true;
    //   // js: document.getElementById("upload").submit()
    //   this.$refs.upload.submit();
    // },
    // //上传成功
    // fileUploadSuccess(response) {
    //   //提示信息
    //   this.loading = false;
    //   this.$message({
    //     type: "success",
    //     message: "添加课程分类成功",
    //   });
    //   //跳转课程分类列表
    //   //路由跳转
    //   this.$router.push({ path: "/subject/list" });
    // },
    // //上传失败
    // fileUploadError() {
    //   this.loading = false;
    //   this.$message({
    //     type: "error",
    //     message: "添加课程分类失败",
    //   });
    // },
  },
};
</script>
