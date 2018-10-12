package com.dcs.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AJob implements IJob {
	private static final Logger _log = Logger.getLogger(AJob.class);
	
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		String message = "";
		Map<String, Object> map = null;
		try {			
			map = (Map<String, Object>) ctx.getScheduler().getContext().get(ctx.getJobDetail().getName());
			if (map==null) {
				map = new HashMap<String, Object>();
				ctx.getScheduler().getContext().put(ctx.getJobDetail().getName(), map);
			}
			String status = (String) map.get("status");
			if ("Running".equals(status) && isBlocking()) return;
		} catch (Exception e) {
			_log.error("Error to retrive schedule context", e);			
		}
		
		try {
			map.put("status", "Running");
			Date start = new Date();
			_log.info("*** Start "+getJobName()+" ***");
			
			message = process(ctx);
			Date end = new Date();
			_log.info("*** Finished "+getJobName()+" using "+(end.getTime()-start.getTime())+" ms. ***");
			
			
			map.put("status", "Complete");
			map.put("message", message);
			map.put("finishedTime", new Date());
		} catch (JobException e) {
			_log.error(e);
			_log.info("*** Error "+getJobName()+" ***");
			
			map.put("status", "Error");
			map.put("message", e.getMessage());			
		}
	}
	
}
