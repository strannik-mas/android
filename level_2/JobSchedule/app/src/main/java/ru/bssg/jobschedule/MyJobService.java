package ru.bssg.jobschedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;


public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Intent service = new Intent(getApplicationContext(), MyDownloadService.class);
        getApplicationContext().startService(service);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}