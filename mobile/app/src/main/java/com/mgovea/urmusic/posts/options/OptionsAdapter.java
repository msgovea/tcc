package com.mgovea.urmusic.posts.options;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mgovea.urmusic.ImpulsionamentoActivity;
import com.mgovea.urmusic.InAppBillingActivity;
import com.mgovea.urmusic.MenuActivity;
import com.mgovea.urmusic.async.AsyncDenunciar;
import com.mgovea.urmusic.async.publication.AsyncImpulsionarPublication;
import com.mgovea.urmusic.async.publication.AsyncRemovePublication;
import com.mgovea.urmusic.entity.Denuncia;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.posts.Question;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.posts.QuestionsAdapterHigh;
import com.mgovea.urmusic.posts.QuestionsAdapterPerfil;
import com.mgovea.urmusic.principal.MenuPublicationFragment;
import com.mgovea.urmusic.principal.MenuPublicationHighFragment;
import com.mgovea.urmusic.profile.ProfileTabbedActivity;
import com.mgovea.urmusic.profile.PublicationProfileFragment;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.Menu;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.mgovea.urmusic.R;
import com.mgovea.urmusic.util.Preferencias;;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private Long mIdPublicacao;
    private Long mIdUsuario;
    private List<Menu> mMenus;
    private Context mContext;
    private ProgressDialog progressDialog;

    @Nullable
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public OptionsAdapter(Context context, List<Menu> questions, Long id, Long idUsuarioPublicacao) {
        mContext = context;
        mMenus = questions;
        mIdPublicacao = id;
        mIdUsuario = idUsuarioPublicacao;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.options_list, null, false));
    }

    public List<Menu> getMenus() {
        return mMenus;
    }

    public void setMenus(List<Menu> menus) {
        this.mMenus = menus;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Menu menu = mMenus.get(position);

        switch (mMenus.get(position).getId()) {
            case 1:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_delete));
                break;
            case 2:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_impulsionar));
                break;
            case 3:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_report));
                break;
            default:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_report));

        }
        holder.mNameOption.setText(menu.getOptionTitle());
        holder.mSubNameOption.setText(menu.getOptionSubTitle());
    }

    @Nullable
    public OnItemClickListener getListener() {
        return listener;
    }

    public Menu getItem(int position) {
        return mMenus.get(position);
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    public void loading(boolean load) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setIndeterminate(true);
            }

            if (load) {
                progressDialog.setMessage(mContext.getString(R.string.loading));
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, AsyncRemovePublication.Listener,
            AsyncImpulsionarPublication.Listener, AsyncDenunciar.Listener {

        TextView mNameOption;
        TextView mSubNameOption;
        SimpleDraweeView mIconOption;
        RelativeLayout mMenuOption;

        public ViewHolder(View itemView) {
            super(itemView);

            mNameOption = (TextView) itemView.findViewById(R.id.name_option);
            mSubNameOption = (TextView) itemView.findViewById(R.id.subname_option);
            mIconOption = (SimpleDraweeView) itemView.findViewById(R.id.icon_option);
            mMenuOption = (RelativeLayout) itemView.findViewById(R.id.menu_option);

            mMenuOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            switch (position) {
                case 0:
                    if (mMenus.get(position).getId() == 1) {
                        loading(true);
                        AsyncRemovePublication sinc = new AsyncRemovePublication(this);
                        sinc.execute(mIdPublicacao);
                    } else {
                        final EditText taskEditText = new EditText(mContext);
                        AlertDialog dialog = new AlertDialog.Builder(mContext)
                                .setTitle(mContext.getString(R.string.denuncia))
                                .setMessage(mContext.getString(R.string.denuncia_text))
                                .setView(taskEditText)
                                .setPositiveButton(mContext.getString(R.string.send), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String task = String.valueOf(taskEditText.getText());
                                        if (TextUtils.isEmpty(task)) {
                                            Toast.makeText(mContext, mContext.getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
                                            //taskEditText.setError(mContext.getString(R.string.error_field_required));
                                            //taskEditText.requestFocus();
                                            return;
                                        }
                                        loading(true);

                                        Denuncia denuncia = new Denuncia();
                                        Preferencias pref = new Preferencias(mContext);

                                        denuncia.setDenunciante(pref.getDadosUsuario());
                                        denuncia.setPublicacao(new Publicacao(mIdPublicacao));
                                        denuncia.setMotivo(task);
                                        denuncia.setDenunciado(new Usuario(mIdUsuario));

                                        AsyncDenunciar sinc = new AsyncDenunciar(OptionsAdapter.ViewHolder.this);
                                        sinc.execute(denuncia);
                                    }
                                })
                                .setNegativeButton(mContext.getString(R.string.cancel), null)
                                .create();
                        dialog.show();
                        //DENUNCIAR
                    }
                    break;
                case 1:
                    //loading(true);
                    Intent intent = new Intent(mContext, /*InAppBillingActivity*/ImpulsionamentoActivity.class);
                    intent.putExtra(API.PUBLICACAO, mIdPublicacao);
                    mContext.startActivity(intent);
                    //AsyncImpulsionarPublication sinc = new AsyncImpulsionarPublication(this);
                    //sinc.execute(mIdPublicacao);
                    //IMPULSIONAR
                    break;
            }
            //view.startAnimation(buttonClick);
            Log.e("FUNCIONO", "CLICOU NA OPÇÃO: " + position);
        }

        @Override
        public void onLoadedImpuls(Boolean bool) {
            try {
                ((MenuActivity) mContext).openAlta();
            } catch (Exception e) {
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.putExtra(API.IMPULSIONAMENTO, true);

                mContext.startActivity(intent);
            }

            try {
                QuestionsAdapter.bottomSheetDialogFragment.dismiss();
            } catch (Exception e) {}
            try {
                QuestionsAdapterPerfil.bottomSheetDialogFragment.dismiss();
            } catch (Exception e) {}
            try {
                QuestionsAdapterHigh.bottomSheetDialogFragment.dismiss();
            } catch (Exception e) {}

            loading(false);
        }

        @Override
        public void onLoaded(Boolean bool) {
            try {
                ((QuestionsAdapter) MenuPublicationFragment.mRecyclerView.getAdapter()).removePublicacaoPorID(mIdPublicacao);
            } catch (Exception e) {
            }
            try {
                ((QuestionsAdapterHigh) MenuPublicationHighFragment.mRecyclerView2.getAdapter()).removePublicacaoPorID(mIdPublicacao);
            } catch (Exception e) {
            }
            try {
                ((QuestionsAdapterPerfil) PublicationProfileFragment.mRecyclerView1.getAdapter()).removePublicacaoPorID(mIdPublicacao);
            } catch (Exception e) {
            }

            loading(false);
        }

        @Override
        public void onLoadedError(String s) {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.error));
                builder.setMessage(mContext.getString(R.string.error));
                builder.setPositiveButton(mContext.getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //b.finish();
                    }
                });
                builder.setCancelable(false);
                loading(false);
                builder.show();
            } catch (Exception e) {
                loading(false);
                e.printStackTrace();
                //TODO MSG ERRO APP QUEBRADO
            }
        }

        @Override
        public void onLoaded(Object o) {
            loading(false);

            try {
                QuestionsAdapter.bottomSheetDialogFragment.dismiss();
            } catch (Exception e) {}
            try {
                QuestionsAdapterPerfil.bottomSheetDialogFragment.dismiss();
            } catch (Exception e) {}
            try {
                QuestionsAdapterHigh.bottomSheetDialogFragment.dismiss();
            } catch (Exception e) {}
            Toast.makeText(mContext, mContext.getString(R.string.denuncia_ok), Toast.LENGTH_LONG).show();
        }

    }


}
