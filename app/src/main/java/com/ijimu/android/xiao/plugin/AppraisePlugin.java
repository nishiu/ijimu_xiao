package com.ijimu.android.xiao.plugin;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;
import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.ContextHolder;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.MainActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppraisePlugin {

    public interface AppraiseComplete{
        void complete();
    }
    private Logger logger = LoggerFactory.getLogger(getClass());
    private LocalStorage localStorage;
    private ReviewManager reviewManager;
    private Task<ReviewInfo> request;
    private ReviewInfo reviewInfo;

    public AppraisePlugin() {
        localStorage = BeanFactory.getBean(LocalStorage.class);
        reviewManager = ReviewManagerFactory.create(ContextHolder.get());
    }

    public void preLoadGPAppraise(){
        if(!localStorage.get(Constants.APPRAISE,true))return;
        request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                reviewInfo = task.getResult();
                logger.info(reviewInfo.toString());
            } else {
                // There was some problem, log or handle the error code.
                @ReviewErrorCode int reviewErrorCode = ((ReviewException) task.getException()).getErrorCode();
                logger.error("get GP review failed, code : "+reviewErrorCode);
            }
        });
    }

    public void showGPAppraise(AppraiseComplete appraiseComplete){
        if(reviewInfo == null || !localStorage.get(Constants.APPRAISE,true)){
            if(appraiseComplete != null)appraiseComplete.complete();
            return;
        }
        Task<Void> flow = reviewManager.launchReviewFlow(MainActivity.getInstance(), reviewInfo);
        flow.addOnCompleteListener(task -> {
            // The flow has finished. The API does not indicate whether the user
            // reviewed or not, or even whether the review dialog was shown. Thus, no
            // matter the result, we continue our app flow.
            localStorage.put(Constants.APPRAISE,false);
            if(appraiseComplete != null)appraiseComplete.complete();
        });
    }
}
