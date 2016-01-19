package perev.hiof.com.playingwithintentstest;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class MyIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String PRODUCE = "perev.hiof.com.playingwithintentstest.action.PRODUCE";

    public static final String PRODUCT = "perev.hiof.com.playingwithintentstest.extra.PRODUCT";
    public static final String AMOUNT = "perev.hiof.com.playingwithintentstest.extra.AMOUNT";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Produce with the given parameters. If
     * the service is already performing a task this action will be queued.intent
     *
     * @see IntentService
     */
    public static void startActionProduce(Context context, String product, int amount) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(PRODUCE);
        intent.putExtra(PRODUCT, product);
        intent.putExtra(AMOUNT, amount);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (PRODUCE.equals(action)) {
                final String product = intent.getStringExtra(PRODUCT);
                final int amount = intent.getIntExtra(AMOUNT, 100);
                handleActionProduce(product, amount);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionProduce(String product, int amount) {
        Log.i("MyIntentService", "Producing: " + product + " - Quantity: " + amount);
        try{
            Thread.sleep(4000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
