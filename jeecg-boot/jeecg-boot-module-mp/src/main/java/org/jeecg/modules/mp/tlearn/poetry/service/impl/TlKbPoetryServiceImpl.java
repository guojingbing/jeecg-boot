package org.jeecg.modules.mp.tlearn.poetry.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.http.HttpUtil;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;
import org.jeecg.modules.mp.tlearn.poetry.mapper.TlKbPoetryCommentMapper;
import org.jeecg.modules.mp.tlearn.poetry.mapper.TlKbPoetryMapper;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * @Description: 拾贝古诗词知识库
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbPoetryServiceImpl extends ServiceImpl<TlKbPoetryMapper, TlKbPoetry> implements ITlKbPoetryService {
	@Autowired
	private TlKbPoetryMapper tlKbPoetryMapper;
	@Autowired
	private TlKbPoetryCommentMapper tlKbPoetryCommentMapper;
	
	@Override
	@Transactional
	public void saveMain(TlKbPoetry tlKbPoetry, List<TlKbPoetryComment> tlKbPoetryCommentList) {
		tlKbPoetryMapper.insert(tlKbPoetry);
		if(tlKbPoetryCommentList!=null && tlKbPoetryCommentList.size()>0) {
			for(TlKbPoetryComment entity:tlKbPoetryCommentList) {
				//外键设置
				entity.setPoetryFkId(tlKbPoetry.getId());
				tlKbPoetryCommentMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(TlKbPoetry tlKbPoetry,List<TlKbPoetryComment> tlKbPoetryCommentList) {
		tlKbPoetryMapper.updateById(tlKbPoetry);
		
		//1.先删除子表数据
		tlKbPoetryCommentMapper.deleteByMainId(tlKbPoetry.getId());
		
		//2.子表数据重新插入
		if(tlKbPoetryCommentList!=null && tlKbPoetryCommentList.size()>0) {
			for(TlKbPoetryComment entity:tlKbPoetryCommentList) {
				//外键设置
				entity.setPoetryFkId(tlKbPoetry.getId());
				tlKbPoetryCommentMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		tlKbPoetryCommentMapper.deleteByMainId(id);
		tlKbPoetryMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tlKbPoetryCommentMapper.deleteByMainId(id.toString());
			tlKbPoetryMapper.deleteById(id);
		}
	}

	@Override
	public IPage<Map> loadList4API(String poeFormId, String dynId, int pageSize, int pageNo, String key, String tagStr, String bookId) {
		Page<Map> page = new Page<>(pageNo, pageSize);
		return tlKbPoetryMapper.loadList4API(page,poeFormId,dynId, key, tagStr, bookId);
	}

	@Override
	public void crawlPoetryAndSave(){
		//从接口获取诗词列表
		String URL_POETRYS="https://app.gushiwen.cn/api/shiwen/Default11.aspx";
		Map params=new HashMap();
		params.put("page",30);
		params.put("token","gswapi");
		JSONObject json= null;
		try {
			json = HttpUtil.getForJSONObject(URL_POETRYS,params,null,null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int sumPage=json.getIntValue("sumPage");
		JSONArray poetrys=json.getJSONArray("gushiwens");
	}

	@Override
	public List<TlKbPoetry> listPoetryRepeat(){
		return tlKbPoetryMapper.listPoetryRepeat();
	}

	@Override
	public List<TlKbPoetry> listPoetryNoRank(String rank){
		return tlKbPoetryMapper.listPoetryNoRank(rank);
	}

	@Override
	public void delPoetrysRepeat(){
		List<TlKbPoetry> poetrys=listPoetryRepeat();
		Map pMap=new HashMap<>();
		List<String> idList=new ArrayList<>();
		for(TlKbPoetry p:poetrys){
			if(!pMap.containsKey(p.getSourceKey())){
				pMap.put(p.getSourceKey(),p.getId());
				idList.add(p.getId());
			}
		}
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<idList.size();i++){
			if(i>0){
				sb.append(",");
			}
			sb.append(idList.get(i));
		}
		tlKbPoetryMapper.delPoetrysRepeat(sb.toString());
	}
}
