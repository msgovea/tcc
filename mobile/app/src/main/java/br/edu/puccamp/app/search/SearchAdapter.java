package br.edu.puccamp.app.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.publication.AsyncLikePublication;
import br.edu.puccamp.app.entity.Curtida;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.comments.CommentsActivity;
import br.edu.puccamp.app.posts.options.CustomBottomSheetDialogFragment;
import br.edu.puccamp.app.profile.ProfileTabbedActivity;
import br.edu.puccamp.app.util.API;

import static android.content.Context.MODE_PRIVATE;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Usuario> mQuestions;
    private Context mContext;
    private Usuario usuario;

    @Nullable
    private OnItemClickListener listener;
    private BottomSheetDialogFragment bottomSheetDialogFragment;


    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public SearchAdapter(Context context, List<Usuario> questions) {
        mContext = context;
        mQuestions = questions;

        SharedPreferences prefs = context.getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        Gson gson = new Gson();
        usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, null, false));
    }

    public List<Usuario> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Usuario> questions) {
        this.mQuestions = questions;
    }

    private String trataData(String data) {
        try {
            String dataFinal;

            String[] partes = data.split("-");

            dataFinal = partes[2] + " " + theMonth(Integer.parseInt(partes[1])) + " " + partes[0];

            return dataFinal;
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public String theMonth(int month) {
        String[] monthNames = mContext.getResources().getStringArray(R.array.month); //{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Usuario question = mQuestions.get(position);

        byte[] byteArray;

        Bitmap bitmap = null;

        //TODO MGOVEA IMAGEM QUEBRANDO
        try {
            //TODO IMAGEM
//            byteArray = Base64.decode(question.getUsuario().getImagemPerfil().getBytes(), Base64.DEFAULT);
//            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            holder.avatar.setImageBitmap(bitmap);
        } catch (Exception e) {
            holder.avatar.setImageDrawable(mContext.getDrawable(R.drawable.ic_account_box_black_24dp));
        }


        holder.textAuthorName.setText(question.getNome() + " - (" + question.getApelido() + ")");
        holder.textJobTitle.setText(question.getCidade() + " - " + question.getEstado());

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(1000);
        //holder.firstFilter.setBackgroundDrawable(drawable);
        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setCornerRadius(1000);
        //holder.secondFilter.setBackgroundDrawable(drawable1);
    }

    @Nullable
    public OnItemClickListener getListener() {

        return listener;
    }

    public Usuario getItem(int position) {
        return mQuestions.get(position);
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textAuthorName;
        TextView textJobTitle;
        ImageView avatar;
        RelativeLayout all;

        public ViewHolder(View itemView) {
            super(itemView);

            textAuthorName = (TextView) itemView.findViewById(R.id.user_name_usuario);
            textJobTitle = (TextView) itemView.findViewById(R.id.location_usuario);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_usuario);
            all = (RelativeLayout) itemView.findViewById(R.id.usuario);

            all.setOnClickListener(this);
//            avatar.setOnClickListener(this);
//            textAuthorName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Log.e("1", view.getId() + "");

            Intent intent;
            Gson gson = new Gson();
            SharedPreferences prefs = mContext.getSharedPreferences(API.USUARIO, MODE_PRIVATE);

            switch (view.getId()) {

                case R.id.usuario:
                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getCodigoUsuario()));
                    view.getContext().startActivity(intent);
                    break;
//                case R.id.user_name_usuario:
//                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
//                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getCodigoUsuario()));
//                    view.getContext().startActivity(intent);
//                    break;
                default:
                    Log.e("mgoveaaa", view.getId() + "");
            }
        }

    }
}
