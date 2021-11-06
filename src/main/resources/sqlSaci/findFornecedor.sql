DO @QUERY := :query;
DO @QUERYLIKE := CONCAT(@QUERY, '%');
DO @QUERYNUM := IF(@QUERY LIKE '^[0-9]+$', @QUERY * 1, 0);

DROP TEMPORARY TABLE IF EXISTS sqldados.T_VEND;
CREATE TEMPORARY TABLE sqldados.T_VEND (
  PRIMARY KEY (no)
)
SELECT V.no,
       V.auxLong4,
       V.name ,
       V.email,
       V.remarks,
       V.cgc
FROM sqldados.vend            AS V
  INNER JOIN sqldados.vendgrv AS VG
	       ON VG.vendno = V.no
  INNER JOIN sqldados.vendgr  AS G
	       ON G.no = VG.vendgrno AND G.name = 'SERVICO'
WHERE (V.no = @QUERYNUM) OR (V.name LIKE @QUERYLIKE) OR (@QUERY = '');

SELECT V.no            AS vendno,
       IFNULL(C.no, 0) AS custno,
       V.name          AS fornecedor,
       V.auxLong4      AS fornecedorSap,
       V.email         AS email,
       V.remarks       AS obs
FROM sqldados.T_VEND       AS V
  LEFT JOIN sqldados.custp AS C
	      ON C.cpf_cgc = V.cgc
GROUP BY vendno

