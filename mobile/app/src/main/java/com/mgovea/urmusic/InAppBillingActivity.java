package com.mgovea.urmusic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mgovea.urmusic.async.publication.AsyncImpulsionarPublication;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.util.IabHelper;
import com.mgovea.urmusic.util.util.IabResult;
import com.mgovea.urmusic.util.util.Inventory;
import com.mgovea.urmusic.util.util.Purchase;

public class InAppBillingActivity extends AbstractAsyncActivity implements AsyncImpulsionarPublication.Listener {

    private static final String TAG =
            "InAppBilling";
    IabHelper mHelper;
    static final String ITEM_SKU = "impulsionamento_1";

    private Button clickButton;
    private Button buyButton;

    private Long mIdPublicacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_billing);

        mIdPublicacao = getIntent().getLongExtra(API.PUBLICACAO, 0);

        buyButton = (Button) findViewById(R.id.buyButton);
        clickButton = (Button) findViewById(R.id.clickButton);
        clickButton.setEnabled(false);
        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmQnPvT6keTTAwXfCpWNNOq5zI9SXRXAmB6REi59AMBl9X24Q7T/u83iYLszg42tpGHIn0rONt5cWGjVpKkBq00j2BWBdiTJVXOL6r+q9HSPUf/r+elaRIh2+2/KyqL/Nf9ULzBEfDliTqxJElYcSxVAHxU625CqQjiGcF436fNYxnUYMOY2kGMQrUmLwtQad9Y+EmjxVdPmCtUIarRq7Ui3fB6wsiM4e4NWyr75ub70K5D9F009rJqwbNvAW8w16jhRDKzuu1OBYqxF2oC82Mcx+dD6DQ7gF0fjJolRuPa4l/h0A2RuRUtULLwL/DMCJsD49gHJzJZurg3MmVMqWbwIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                               Log.d(TAG, "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d(TAG, "In-app Billing is set up OK");
                                           }
                                       }
                                   });

        getSupportActionBar().setTitle(getString(R.string.impulsionar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void buttonClicked(View view) {
        clickButton.setEnabled(false);
        buyButton.setEnabled(true);
    }

    public void buyClick(View view) {
        try {
            mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001,
                    mPurchaseFinishedListener, "impulsionamento_1");
        } catch (Exception e){
            showErrorMessage();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                return;
            } else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
                buyButton.setEnabled(false);
            }

        }
    };

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        if (!mIdPublicacao.equals(0)) {
                            showLoadingProgressDialog();
                            AsyncImpulsionarPublication sinc = new AsyncImpulsionarPublication(InAppBillingActivity.this);
                            sinc.execute(mIdPublicacao);
                        }
                        clickButton.setEnabled(true);
                    } else {
                        // handle error
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }


    @Override
    public void onLoadedImpuls(Boolean bool) {
        try {
            //QuestionsAdapter.bottomSheetDialogFragment.dismiss();

            dismissProgressDialog();

            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra(API.IMPULSIONAMENTO, true);

            startActivity(intent);
        } catch (Exception e) {

        }


    }

    @Override
    public void onLoadedError(String s) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString(R.string.error));
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //b.finish();
                }
            });
            builder.setCancelable(false);
            dismissProgressDialog();
            builder.show();
        } catch (Exception e) {
            dismissProgressDialog();
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }
}
