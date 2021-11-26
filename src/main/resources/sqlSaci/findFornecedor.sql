DO @QUERY := :query;
DO @QUERYLIKE := CONCAT(@QUERY, '%');
DO @QUERYNUM := IF(@QUERY REGEXP '^[0-9]+$', @QUERY * 1, 0);

DROP TEMPORARY TABLE IF EXISTS sqldados.T_VEND;
CREATE TEMPORARY TABLE sqldados.T_VEND (
  PRIMARY KEY (no)
)
SELECT V.no,
       V.auxLong4,
       V.name,
       V.email,
       V.remarks,
       V.cgc
FROM sqldados.vend            AS V
  INNER JOIN sqldados.vendgrv AS VG
	       ON VG.vendno = V.no
  INNER JOIN sqldados.vendgr  AS G
	       ON G.no = VG.vendgrno AND G.name = 'SERVICO'
WHERE (V.no = @QUERYNUM)
   OR (V.name LIKE @QUERYLIKE AND @QUERYNUM != 0)
   OR (@QUERY = '');

DROP TEMPORARY TABLE IF EXISTS sqldados.T_FORNECEDOR;
CREATE TEMPORARY TABLE sqldados.T_FORNECEDOR (
  PRIMARY KEY (vendno)
)
SELECT V.no            AS vendno,
       IFNULL(C.no, 0) AS custno,
       V.name          AS fornecedor,
       V.remarks       AS obs
FROM sqldados.T_VEND       AS V
  LEFT JOIN sqldados.custp AS C
	      ON C.cpf_cgc = V.cgc
GROUP BY vendno;

SELECT F.vendno,
       F.custno,
       F.fornecedor,
       F.obs,
       I.storeno AS loja,
       I.c9      AS observacao,
       I.s28     AS status
FROM sqldados.inv                  AS I
  INNER JOIN sqldados.T_FORNECEDOR AS F
	       USING (vendno)
WHERE I.bits & POW(2, 4) = 0
  AND I.auxShort13 & POW(2, 15) = 0
  AND (I.s28 = :status || :status = 0)
GROUP BY vendno, storeno



