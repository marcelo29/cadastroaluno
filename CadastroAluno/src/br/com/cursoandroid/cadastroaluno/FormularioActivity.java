package br.com.cursoandroid.cadastroaluno;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.cursoandroid.cadastroaluno.helper.FormularioHelper;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;
import br.com.cursoandroid.cadastroaluno.modelo.dao.AlunoDAO;

public class FormularioActivity extends ActionBarActivity {

	private Button botao;
	private FormularioHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);

		helper = new FormularioHelper(this);
		botao = (Button) findViewById(R.id.sbSalvar);
		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Aluno aluno = helper.getAluno();

				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
				dao.cadastrar(aluno);
				dao.close();
				
				finish();
			}
		});
	}
}