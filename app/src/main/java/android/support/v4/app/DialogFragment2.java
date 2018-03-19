package android.support.v4.app;

import android.support.annotation.CallSuper;


public class DialogFragment2 extends DialogFragment {
    private static final int THRESHOLD_TIME = 300;
    private long managerTime;
    private long transactionTime;

    @CallSuper
    @Override
    public int show(FragmentTransaction transaction, String tag) {
        try {
            if (System.currentTimeMillis() - transactionTime < THRESHOLD_TIME)
                return mBackStackId;
            transactionTime = System.currentTimeMillis();
            if (isAdded()) {
                return mBackStackId;
            }
            mDismissed = false;
            mShownByMe = true;
            transaction.add(this, tag);
            mViewDestroyed = false;
            mBackStackId = transaction.commitAllowingStateLoss();
        } catch (Throwable e) {
        }
        return mBackStackId;
    }

    @CallSuper
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            if (System.currentTimeMillis() - managerTime < THRESHOLD_TIME)
                return;
            managerTime = System.currentTimeMillis();
            if (isAdded()) {
                return;
            }
            mDismissed = false;
            mShownByMe = true;
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (Throwable e) {
        }
    }

}
