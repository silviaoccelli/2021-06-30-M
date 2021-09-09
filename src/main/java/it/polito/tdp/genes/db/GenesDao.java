package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	public List<Integer> getVertici() {
		String sql = "SELECT DISTINCT G.chromosome AS ch "
				+ "FROM genes AS G "
				+ "HAVING G.chromosome != 0 ";
		
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {


				result.add(res.getInt("ch"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	public List<Adiacenza> getArchi() {
		String sql = "SELECT G1.Chromosome AS c1, G2.Chromosome AS c2, SUM(DISTINCT I.Expression_Corr) AS peso "
				+ "FROM genes AS G1, genes AS G2, interactions AS I "
				+ "WHERE G1.Chromosome != G2.Chromosome "
				+ "AND G1.GeneID = I.GeneID1 "
				+ "AND G2.GeneID = I.GeneID2 "
				+ "AND G1.GeneID != G2.GeneID "
				+ "AND G1.Chromosome != 0 "
				+ "AND G2.Chromosome != 0 "
				+ "GROUP BY G1.Chromosome, G2.Chromosome";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {


			result.add(new Adiacenza(res.getInt("c1"), res.getInt("c2"), res.getDouble("peso")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	


	
}
