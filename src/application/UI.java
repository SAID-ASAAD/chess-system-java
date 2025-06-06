package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	public static final String ANSI_RESET1 = "\u001B[0m";
	public static final String ANSI_BLACK1 = "\u001B[30m";
	public static final String ANSI_RED1 = "\u001B[31m";
	public static final String ANSI_GREEN1 = "\u001B[32m";
	public static final String ANSI_YELLOW1 = "\u001B[33m";
	public static final String ANSI_BLUE1 = "\u001B[34m";
	public static final String ANSI_PURPLE1 = "\u001B[35m";
	public static final String ANSI_CYAN1 = "\u001B[36m";
	public static final String ANSI_WHITE1 = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND1 = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND1 = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND1 = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND1 = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND1 = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND1 = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND1 = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND1 = "\u001B[47m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		if (!chessMatch.getCheckMate()) {
			System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
			if (chessMatch.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("CHECKMATE!");
			System.out.println("Winer: " + chessMatch.getCurrentPlayer());
		}
	}

	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPieceW(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves, ChessMatch cm) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < pieces.length; j++) {
				if (cm.getCurrentPlayer() == Color.WHITE) {
				printPieceW(pieces[i][j], possibleMoves[i][j]);
				}
				else {
					printPieceB(pieces[i][j], possibleMoves[i][j]);
				}
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printPieceW(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_CYAN_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET1);
		} 
		else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE1 + piece + ANSI_RESET1);
			} 
			else {
				System.out.print(ANSI_RED1 + piece + ANSI_RESET1);
			}
		}
		System.out.print(" ");
	}
	public static void printPieceB(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_YELLOW_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET1);
		} 
		else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE1 + piece + ANSI_RESET1);
			} 
			else {
				System.out.print(ANSI_RED1 + piece + ANSI_RESET1);
			}
		}
		System.out.print(" ");
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE)
				.collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK)
				.collect(Collectors.toList());
		System.out.println("Captured pieces: ");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Black: ");
		System.out.print(ANSI_RED);
		System.out.print(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}
}
