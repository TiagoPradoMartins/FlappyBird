package com.pharaohnics.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoTopoMaior;
	private Texture canoBaixoMaior;
	private Random numeroRandomico;
	private BitmapFont fonte;


	//Atributos de configuraçao
	private int 	larguraDispositivo;
	private int 	alturaDispositivo;
	private float 	variacaoPassaro = 0;
	private float 	velocidadeQueda = 0;
	private float 	posicaoVerticalInicial;
	private float 	posicaoMovimentoCanoHorizontal;
	private float 	espacoEntreCanos;
	private float 	deltaTime;
	private float 	alturaEntreCanosRandomica;
	private int		estadoJogo  = 0;
	private int     pontuacao   = 0;



	@Override
	public void create () {
		//Gera numero randomico
	    numeroRandomico = new Random();

		//Cria o SpriteBatch com o nome de batch
		batch = new SpriteBatch();

        fonte = new BitmapFont(); // cria o BitmapFont com o nome de fonte
        fonte.setColor(Color.WHITE); // seta a cor da fonte como branca
        fonte.getData().scale(6); // seta o tamanho da fonte como 6

		//Cria e define as imagens para o passaro
		passaros  = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		//Cria a imagem de fundo
		fundo 	= new Texture("fundo.png");

        //Cria e define as imagens para os canos
		canoBaixoMaior = new Texture("cano_baixo_maior.png");
		canoTopoMaior = new Texture("cano_topo_maior.png");


		//Recebe nos atributos a altura e largura da tela
		larguraDispositivo 	= Gdx.graphics.getWidth();
		alturaDispositivo 	= Gdx.graphics.getHeight();
		posicaoVerticalInicial = alturaDispositivo / 2;
		posicaoMovimentoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 300;

	}

	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime(); //Resgata o delta time
		variacaoPassaro += deltaTime *5 ;
		if (variacaoPassaro > 2) variacaoPassaro = 0;

		if(estadoJogo == 0){
			if(Gdx.input.justTouched())
				estadoJogo = 1;
		}else {

			posicaoMovimentoCanoHorizontal -= deltaTime * 200;

			velocidadeQueda++;

			if (Gdx.input.justTouched())
				velocidadeQueda = -15;
			//Verifica se o passaro passou do ponto inicial vertical ou se a tela foi pressionada
			if (posicaoVerticalInicial > 0 || velocidadeQueda < 0)
				posicaoVerticalInicial -= velocidadeQueda;

			//Verifica se o cano saiu inteiramente da tela
			if (posicaoMovimentoCanoHorizontal < -canoTopoMaior.getWidth()) {
				posicaoMovimentoCanoHorizontal = larguraDispositivo;
				alturaEntreCanosRandomica = numeroRandomico.nextInt(500) - 250;
			}

		}

		//Cria o batch , aqui dentro vai somente os objetos apresentados na tela
		batch.begin(); 	//Inicio do batch

		batch.draw(fundo,0,0, larguraDispositivo, alturaDispositivo );

		batch.draw(canoTopoMaior,posicaoMovimentoCanoHorizontal , alturaDispositivo /2 + espacoEntreCanos /2 + alturaEntreCanosRandomica );
		batch.draw(canoBaixoMaior,posicaoMovimentoCanoHorizontal , alturaDispositivo /2 - canoBaixoMaior.getHeight() - espacoEntreCanos /2 + alturaEntreCanosRandomica);
		batch.draw(passaros[(int) variacaoPassaro], 120, posicaoVerticalInicial );
		fonte.draw(batch , String.valueOf(pontuacao),larguraDispositivo / 2 , alturaDispositivo -50 );

		batch.end();	//Final do batch

	}
	

}
