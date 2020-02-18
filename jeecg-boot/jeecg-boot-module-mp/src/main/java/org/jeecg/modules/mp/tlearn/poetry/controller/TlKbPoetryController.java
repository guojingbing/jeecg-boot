package org.jeecg.modules.mp.tlearn.poetry.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryTag;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryCommentService;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryService;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryTagService;
import org.jeecg.modules.mp.tlearn.poetry.vo.TlKbPoetryPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 拾贝古诗词知识库
 * @Author:
 * @Date: 2020-01-03
 * @Version: V1.0
 */
@RestController
@RequestMapping("/mp/tl/kb/poe")
@Slf4j
public class TlKbPoetryController {
    @Autowired
    private ITlKbPoetryService tlKbPoetryService;
    @Autowired
    private ITlKbPoetryCommentService tlKbPoetryCommentService;
    @Autowired
    private ITlKbPoetryTagService tlKbPoetryTagService;

    /**
     * 分页列表查询
     *
     * @param tlKbPoetry
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(TlKbPoetry tlKbPoetry,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<TlKbPoetry> queryWrapper = QueryGenerator.initQueryWrapper(tlKbPoetry, req.getParameterMap());
        Page<TlKbPoetry> page = new Page<TlKbPoetry>(pageNo, pageSize);
        IPage<TlKbPoetry> pageList = tlKbPoetryService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param tlKbPoetryPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody TlKbPoetryPage tlKbPoetryPage) {
        TlKbPoetry tlKbPoetry = new TlKbPoetry();
        BeanUtils.copyProperties(tlKbPoetryPage, tlKbPoetry);
        tlKbPoetryService.saveMain(tlKbPoetry, tlKbPoetryPage.getTlKbPoetryCommentList());
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param tlKbPoetryPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody TlKbPoetryPage tlKbPoetryPage) {
        TlKbPoetry tlKbPoetry = new TlKbPoetry();
        BeanUtils.copyProperties(tlKbPoetryPage, tlKbPoetry);
        TlKbPoetry tlKbPoetryEntity = tlKbPoetryService.getById(tlKbPoetry.getId());
        if (tlKbPoetryEntity == null) {
            return Result.error("未找到对应数据");
        }
        tlKbPoetryService.updateMain(tlKbPoetry, tlKbPoetryPage.getTlKbPoetryCommentList());
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        tlKbPoetryService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.tlKbPoetryService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        TlKbPoetry tlKbPoetry = tlKbPoetryService.getById(id);
        if (tlKbPoetry == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(tlKbPoetry);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryTlKbPoetryCommentByMainId")
    public Result<?> queryTlKbPoetryCommentListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<TlKbPoetryComment> tlKbPoetryCommentList = tlKbPoetryCommentService.selectByMainId(id);
        return Result.ok(tlKbPoetryCommentList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryTlKbPoetryTagByMainId")
    public Result<?> queryTlKbPoetryTagListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<TlKbPoetryTag> tlKbPoetryTagList = tlKbPoetryTagService.selectByMainId(id);
        return Result.ok(tlKbPoetryTagList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param tlKbPoetry
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TlKbPoetry tlKbPoetry) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<TlKbPoetry> queryWrapper = QueryGenerator.initQueryWrapper(tlKbPoetry, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<TlKbPoetry> queryList = tlKbPoetryService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<TlKbPoetry> tlKbPoetryList = new ArrayList<TlKbPoetry>();
        if (oConvertUtils.isEmpty(selections)) {
            tlKbPoetryList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            tlKbPoetryList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<TlKbPoetryPage> pageList = new ArrayList<TlKbPoetryPage>();
        for (TlKbPoetry main : tlKbPoetryList) {
            TlKbPoetryPage vo = new TlKbPoetryPage();
            BeanUtils.copyProperties(main, vo);
            List<TlKbPoetryComment> tlKbPoetryCommentList = tlKbPoetryCommentService.selectByMainId(main.getId());
            vo.setTlKbPoetryCommentList(tlKbPoetryCommentList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "拾贝古诗词知识库列表");
        mv.addObject(NormalExcelConstants.CLASS, TlKbPoetryPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("拾贝古诗词知识库数据", "导出人:" + sysUser.getRealname(), "拾贝古诗词知识库"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TlKbPoetryPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TlKbPoetryPage.class, params);
                for (TlKbPoetryPage page : list) {
                    TlKbPoetry po = new TlKbPoetry();
                    BeanUtils.copyProperties(page, po);
                    tlKbPoetryService.saveMain(po, page.getTlKbPoetryCommentList());
                }
                return Result.ok("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok("文件导入失败！");
    }

}
