package com.cupcakes.kandycupcakes.IT19152110;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cupcakes.kandycupcakes.R;

import java.util.List;

public class faqList extends ArrayAdapter<FAQ> {

    private Activity context;
    private List<FAQ> faqList;

    public faqList(Activity context ,  List<FAQ> faqList) {

        super(context, R.layout.list, faqList);
        this.context = context;
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list, null , true);

        TextView textViewQuestion = listViewItem.findViewById(R.id.questionL);
        TextView textViewAnswer = listViewItem.findViewById(R.id.answerL);

        FAQ faq = faqList.get(position);

        textViewQuestion.setText(faq.getQue().toString());
        textViewAnswer.setText(faq.getAns().toString());

        return listViewItem;
    }
}
