package main

import "fmt"

func calculo_imc(altura float32, peso float32) float32 {
	return peso / (altura * altura)
}

func faixa_etaria(idade int) string {
	if idade < 60 {
		return "adulto"
	} else {
		return "idoso"
	}
}

func classificacao_imc(altura float32, peso float32, idade int) string {
	imc := calculo_imc(altura, peso)
	var classificacao string

	switch faixa_etaria(idade) {
	case "adulto":
		switch {
		case imc < 18.5:
			classificacao = "Baixo peso"
		case imc < 24.9:
			classificacao = "Peso normal"
		case imc < 29.9:
			classificacao = "Excesso de peso"
		case imc < 34.9:
			classificacao = "Obesidade de Classe 1"
		case imc < 39.9:
			classificacao = "Obesidade de Classe 2"
		default:
			classificacao = "Obesidade de Classe 3"
		}

	case "idoso":
		switch {
		case imc <= 22.0:
			classificacao = "Baixo peso"
		case imc < 27.0:
			classificacao = "Adequado ou eutrófico"
		default:
			classificacao = "Sobrepeso"
		}
	}

	return classificacao
}

func main() {
	var resultado string = classificacao_imc(1.60, 57, 22)

	fmt.Println(resultado)
}
