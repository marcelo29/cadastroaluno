package br.com.cursoandroid.cadastroaluno.helper;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.com.cursoandroid.cadastroaluno.FormularioActivity;
import br.com.cursoandroid.cadastroaluno.R;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;

public class FormularioHelper {

	private EditText nome, telefone, endereco, site, email;
	private SeekBar nota;
	private ImageView foto;

	private Aluno aluno;

	public FormularioHelper(FormularioActivity activity) {

		nome = (EditText) activity.findViewById(R.id.edNome);
		telefone = (EditText) activity.findViewById(R.id.edFone);
		endereco = (EditText) activity.findViewById(R.id.edEndereco);
		site = (EditText) activity.findViewById(R.id.edSite);
		email = (EditText) activity.findViewById(R.id.edEmail);
		nota = (SeekBar) activity.findViewById(R.id.sbNota);
		foto = (ImageView) activity.findViewById(R.id.foto);

		aluno = new Aluno();
	}

	public Aluno getAluno() {

		aluno.setNome(nome.getText().toString());
		aluno.setTelefone(telefone.getText().toString());
		aluno.setEndereco(endereco.getText().toString());
		aluno.setSite(site.getText().toString());
		aluno.setEmail(email.getText().toString());
		aluno.setNota(Double.valueOf(nota.getProgress()));

		return aluno;
	}
}