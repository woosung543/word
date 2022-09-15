package com.mycom.word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
	ArrayList<Word> list;
	Scanner s;
	public final String fname = "Dictionary.txt";
	
	WordCRUD(Scanner s){
		list = new ArrayList<Word>();
		this.s = s;
	}
	@Override
	public Object add() {
		System.out.print("=> 난이도(1,2,3) & 새 단어 입력 : ");
		int level = s.nextInt();
		String word = s.nextLine();
		
		System.out.print("뜻 입력 : ");
		String meaning = s.nextLine();
		System.out.println();
		return new Word(1, level, word, meaning);
	}
	
	public void addWord() {
		Word one = (Word)add();
		list.add(one);
		System.out.println("새 단어가 단어장에 추가되었습니다!!!\n");
	}

	@Override
	public int update(Object obj) {
		return 0;
	}

	@Override
	public int delete(Object obj) {
		return 0;
	}

	@Override
	public void selectOne(int id) {
	}

	public void listAll() {
		System.out.println("-------------------");
		for(int i=0; i<list.size(); i++) {
			System.out.println((i+1)+list.get(i).toString());
		}
		System.out.println("-------------------");
	}
	
	public ArrayList<Integer> listAll(String keyword) {
		
		ArrayList<Integer> idList = new ArrayList<>();
		int j = 0;
		
		System.out.println("-------------------");
		for(int i=0; i<list.size(); i++) {
			if(!list.get(i).getWord().contains(keyword)) continue;
			System.out.println((j+1)+list.get(i).toString());
			idList.add(i);
			j++;
		}
		System.out.println("-------------------");
		
		return idList;
	}

	public void updateItem() {
		System.out.print("=> 수정할 단어 검색 : ");
		String keyword = s.next();
		ArrayList<Integer> idlist = this.listAll(keyword);
		System.out.println("=> 수정할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine();
		System.out.println("=> 뜻 입력 : ");
		String meaning = s.nextLine();
		Word word = list.get(idlist.get(id-1));
		word.setMeaning(meaning);
		System.out.println("단어가 수정되었습니다. ");
	}
	public void deleteItem() {
		System.out.print("=> 삭제할 단어 검색 : ");
		String keyword = s.next();
		ArrayList<Integer> idlist = this.listAll(keyword);
		System.out.println("=> 삭제할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine();
		System.out.print("=> 정말로 삭제하겠습니까?(Y/N) : ");
		String ans = s.nextLine();
		if(ans.equalsIgnoreCase("y")) {
			list.remove((int)idlist.get(id-1));
			System.out.println("단어가 삭제되었습니다. ");
		}
		else
			System.out.println("취소되었습니다. ");	
	}
	
	public void loadFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line;
			int count = 0;
			
			while(true) {
				line = br.readLine();
				if(line == null) break;
				String data[] = line.split("\\|");
				int level = Integer.parseInt(data[0]);
				String word = data[1];
				String meaning = data[2];
				list.add(new Word(1, level, word, meaning));
				count++;
			}
			br.close();
			System.out.println("=>" + count + "개 로딩 완료!!!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveFile() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(fname));
			for(Word w : list) {
				pw.write(w.toFileString()+"\n");
			}
			pw.close();
			System.out.println("=> 데이터 저장 완료!!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
