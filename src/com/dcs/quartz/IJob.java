package com.dcs.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public interface IJob extends Job{
	public String getJobName();
	public boolean isBlocking();
	public String process(JobExecutionContext ctx) throws JobException;
}
