package br.com.cursoandroid.cadastroaluno;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;
import br.com.cursoandroid.cadastroaluno.modelo.dao.AlunoDAO;

public class ListaAlunosActivity extends Activity {

	private ListView lvListagem;

	private List<Aluno> listaAlunos;

	private ArrayAdapter<Aluno> adapter;

	private static final String TAG = "CADASTRO_ALUNO";

	private int adapterLayout = android.R.layout.simple_list_item_1;

	private Aluno alunoSelecionado = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listaalunoslayout);
		lvListagem = (ListView) findViewById(R.id.lvListagem);
		registerForContextMenu(lvListagem);
		listaAlunos = new ArrayList<Aluno>();

		lvListagem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
				Toast.makeText(ListaAlunosActivity.this, "Aluno: " + listaAlunos.get(posicao), Toast.LENGTH_SHORT)
						.show();
			}

		});

		lvListagem.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao);
				Log.i(TAG, "Aluno selecionado ListView.longClick()" + alunoSelecionado.getNome());
				return false;
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();

		inflater.inflate(R.menu.menu_principal, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_novo:

			Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
			startActivity(intent);

			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void carregarLista() {
		AlunoDAO dao = new AlunoDAO(this);
		this.listaAlunos = dao.listar();
		dao.close();

		this.adapter = new ArrayAdapter<Aluno>(this, adapterLayout, listaAlunos);
		this.lvListagem.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.carregarLista();
	}

	private void excluirAluno() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Confirmar a exclusao de: " + alunoSelecionado.getNome());

		builder.setPositiveButton("Sim", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
				dao.deletar(alunoSelecionado);
				dao.close();
				carregarLista();
				alunoSelecionado = null;
			}
		});

		builder.setNegativeButton("Nao", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle("Confirmacao de operacao");
		dialog.show();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		getMenuInflater().inflate(R.menu.menu_contexto, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuDeletar:
			excluirAluno();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
}