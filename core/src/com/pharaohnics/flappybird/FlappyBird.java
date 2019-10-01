package com.pharaohnics.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoTopoMaior;
	private Texture canoBaixoMaior;
	private Texture gameOver;
	private Random numeroRandomico;
	private BitmapFont fonte;
	private BitmapFont mensagem;
	private Circle passaroCirculo;
	private Rectangle retanguloCanoTopo;
	private Rectangle retanguloCanoBaixo;
	//private ShapeRenderer shape;



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
	private int		estadoJogo  = 0; //0--> Jogo não iniciado 1--> Jogo iniciado 2-->Game Over
	private int     pontuacao   = 0;
	private boolean marcouPonto;



	@Override
	public void create () {
		//Gera numero randomico
		numeroRandomico = new Random();

		//Cria o SpriteBatch com o nome de batch
		batch = new SpriteBatch();

		//Cria as formas para configurar as colisôes
		passaroCirculo = new Circle();
		retanguloCanoTopo = new Rectangle();
		retanguloCanoBaixo = new Rectangle();

		//shape = new ShapeRenderer();

		//Cria um BitmapFont , setando a cor e o tamanho da fonte do Placar
		fonte = new BitmapFont(); // cria o BitmapFont com o nome de fonte
		fonte.setColor(Color.WHITE); // seta a cor da fonte como branca
		fonte.getData().scale(6); // seta o tamanho da fonte como 6

		//Cria um BitmapFont , setando a cor e o tamanho da fonte da mensagem reinicio de jogo
		mensagem = new BitmapFont();
		mensagem.setColor(Color.WHITE);
		mensagem.getData().scale(3);



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

		//Cria Textura Game over
		gameOver = new Texture("game_over.png");


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
		}else {// Jogo Iniciado

			velocidadeQueda++;
			//Verifica se o passaro passou do ponto inicial vertical ou se a tela foi pressionada
			if (posicaoVerticalInicial > 0 || velocidadeQueda < 0)
				posicaoVerticalInicial -= velocidadeQueda;

			if (estadoJogo == 1){// Se o estador for 1 executa o jogo
				posicaoMovimentoCanoHorizontal -= deltaTime * 200;
				if (Gdx.input.justTouched())
					velocidadeQueda = -15;

				//Verifica se o cano saiu inteiramente da tela
				if (posicaoMovimentoCanoHorizontal < -canoTopoMaior.getWidth()) {
					posicaoMovimentoCanoHorizontal = larguraDispositivo;
					alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
					marcouPonto = false; // marcouPonto recebe false no momento que os canos sao crianos no inicio da tela, para marcar a pontuaçao
				}

				//Verifica pontuaçao
				if (posicaoMovimentoCanoHorizontal < 120){
					if (!marcouPonto) {
						pontuacao++;
						marcouPonto = true; // recebe true para marcar somente uma vez a pontuaçao
					}
				}

			}else{//Game Over
				if (Gdx.input.justTouched()){
					estadoJogo 		= 0;
					pontuacao 		= 0;
					velocidadeQueda = 0;
					marcouPonto = false;
					posicaoVerticalInicial 			= alturaDispositivo / 2;
					posicaoMovimentoCanoHorizontal 	= larguraDispositivo;

				}
			}

		}

		//Cria o batch , aqui dentro vai somente os objetos apresentados na tela
		batch.begin(); 	//Inicio do batch

		batch.draw(fundo,0,0, larguraDispositivo, alturaDispositivo );

		batch.draw(canoTopoMaior , posicaoMovimentoCanoHorizontal , alturaDispositivo /2 + espacoEntreCanos /2 + alturaEntreCanosRandomica );
		batch.draw(canoBaixoMaior , posicaoMovimentoCanoHorizontal , alturaDispositivo /2 - canoBaixoMaior.getHeight() - espacoEntreCanos /2 + alturaEntreCanosRandomica);
		batch.draw(passaros[(int) variacaoPassaro], 120, posicaoVerticalInicial );
		fonte.draw(batch , String.valueOf(pontuacao),larguraDispositivo / 2 , alturaDispositivo -50 );

		if (estadoJogo == 2){
			batch.draw(gameOver , larguraDispositivo / 2 - gameOver.getWidth() / 2 , alturaDispositivo / 2);
			mensagem.draw(batch,"Toque para reiniciar",larguraDispositivo  / 4 ,alturaDispositivo / 2 - gameOver.getHeight() / 2);
		}

		batch.end();	//Final do batch

		//Cria um shape para o passaro do tipo Circulo
		passaroCirculo.set(120 + passaros[0].getWidth() / 2 , posicaoVerticalInicial + passaros[0].getHeight() / 2 , passaros[0].getWidth() / 2 );

		//Cria um shape para os canos Topo e Baixo
		retanguloCanoBaixo.set(
				posicaoMovimentoCanoHorizontal,alturaDispositivo /2 - canoBaixoMaior.getHeight() - espacoEntreCanos /2 + alturaEntreCanosRandomica,
				canoBaixoMaior.getWidth(),canoBaixoMaior.getHeight()
		);

		retanguloCanoTopo.set(
				posicaoMovimentoCanoHorizontal,alturaDispositivo /2 + espacoEntreCanos /2 + alturaEntreCanosRandomica,
				canoTopoMaior.getWidth(),canoTopoMaior.getHeight()
		);

		//Teste de Colisao
		if(Intersector.overlaps(passaroCirculo , retanguloCanoBaixo) || Intersector.overlaps(passaroCirculo , retanguloCanoTopo)
								|| posicaoVerticalInicial <= 0 || posicaoVerticalInicial >= alturaDispositivo){

			estadoJogo = 2; //--> SETA o Estado do jogo Game Over


		}

		//Desenhar formas
		/*shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.circle(passaroCirculo.x , passaroCirculo.y , passaroCirculo.radius);
		shape.rect(retanguloCanoBaixo.x , retanguloCanoBaixo.y , retanguloCanoBaixo.width , retanguloCanoBaixo.height);
		shape.rect(retanguloCanoTopo.x , retanguloCanoTopo.y , retanguloCanoTopo.width , retanguloCanoTopo.height);
		shape.setColor(Color.BLUE);

		shape.end();*/

		//Gdx.app.log("Colisao", "Houve Colisao");
	}


}