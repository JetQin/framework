/** 
 * Project Name:framework 
 * File Name:AsyncResponse.java 
 * Package Name:com.usee.model.common
 * Date:Jun 22, 201510:07:06 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.model.common;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ClassName: AsyncResponse
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
public class AsyncResponse<V> implements Future<V>
{

	private V value;

	private Exception executionException;

	private boolean isCompletedExceptional = false;

	private boolean isCancelled = false;

	private boolean isDone = false;

	private long checkCompleteInterval = 100;

	public AsyncResponse()
	{

	}

	public AsyncResponse(V value)
	{
		this.value = value;
		this.isDone = true;
	}

	public AsyncResponse(Throwable ex)
	{
		this.executionException = new ExecutionException(ex);
		this.isCompletedExceptional = true;
		this.isDone = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Future#cancel(boolean)
	 */
	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		// TODO Auto-generated method stub
		this.isCancelled = true;
		this.isDone = true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Future#isCancelled()
	 */
	@Override
	public boolean isCancelled()
	{
		return this.isCancelled;
	}

	
	public boolean isCompletedExceptional()
	{
		return this.isCompletedExceptional;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Future#isDone()
	 */
	@Override
	public boolean isDone()
	{
		return this.isDone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Future#get()
	 */
	@Override
	public V get() throws InterruptedException, ExecutionException
	{
		// TODO Auto-generated method stub
		block(0);

		if (isCancelled())
		{
			throw new CancellationException();
		}

		if (isCompletedExceptional())
		{
			throw new ExecutionException(this.executionException);
		}

		if (isDone())
		{
			return this.value;
		}
		throw new InterruptedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Future#get(long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
	{
		// TODO Auto-generated method stub
		long timeoutInMillis = unit.toMillis(timeout);
		block(timeoutInMillis);
		
		if(isCancelled())
		{
			throw new CancellationException();
		}
		
		if(isCompletedExceptional())
		{
			throw new ExecutionException(this.executionException);
		}
		
		if(isDone())
		{
			return this.value;
		}		
		
		throw new InterruptedException();
	}

	private void block(long timeout) throws InterruptedException
	{
		long start = System.currentTimeMillis();

		while (!isCancelled() && !isDone())
		{
			if (timeout > 0)
			{
				long now = System.currentTimeMillis();
				if (now > start + timeout)
				{
					break;
				}
			}
			Thread.sleep(checkCompleteInterval);
		}
	}

	public boolean complete(V value)
	{
		this.value = value;
		this.isDone = true;
		return true;
	}

	public boolean completedExceptional(Throwable ex)
	{
		this.value = null;
		this.executionException = new ExecutionException(ex);
		this.isCompletedExceptional = true;
		this.isDone = true;
		return true;
	}
	
	/**
	 * @param checkCompleteInterval the checkCompleteInterval to set
	 */
	public void setCheckCompleteInterval(long checkCompleteInterval)
	{
		this.checkCompleteInterval = checkCompleteInterval;
	}
	
}
