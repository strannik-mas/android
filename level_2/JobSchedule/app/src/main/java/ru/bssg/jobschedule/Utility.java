package ru.bssg.jobschedule;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class Utility {

    public static final int JOB_ID = 444;


    public static void scheduleJob(Context context) {
        ComponentName jobServiceComponent = new ComponentName(context, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, jobServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        //builder.setMinimumLatency(10 * 60 * 1000); // Через 10 минут
        builder.setPeriodic(1*3600*1000); // Каждый час
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo info = builder.build();
        jobScheduler.schedule(info);

//        jobScheduler.cancel(JOB_ID);
    }
}