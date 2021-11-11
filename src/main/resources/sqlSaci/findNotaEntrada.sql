DROP TEMPORARY TABLE IF EXISTS sqldados.T_LITROS;
CREATE TEMPORARY TABLE sqldados.T_LITROS (
  PRIMARY KEY (invno)
)
SELECT invno, SUM(qtty / 1000) AS quant
FROM sqldados.iprd        AS P
  INNER JOIN sqldados.inv AS I
	       USING (invno)
WHERE I.bits & POW(2, 4) = 0
  AND I.auxShort13 & POW(2, 15) = 0
  AND I.vendno = :vendno
  AND (I.storeno = :loja OR :loja = 0)
  AND P.prdno IN ('          980869', '          980019', '          980018')
GROUP BY I.invno;

SELECT I.invno                                                                    AS invno,
       I.storeno                                                                  AS loja,
       S.otherName                                                                AS siglaLoja,
       CAST(I.issue_date AS DATE)                                                 AS dataEmissao,
       CAST(I.date AS DATE)                                                       AS dataEntrada,
       CAST(CONCAT(I.nfname, IF(I.invse = '', '', CONCAT('/', I.invse))) AS CHAR) AS nota,
       grossamt / 100                                                             AS valor,
       I.remarks                                                                  AS obs,
       I.vendno                                                                   AS vendno,
       CAST(MIN(X.duedate) AS DATE)                                               AS dataVencimento,
       I.c10                                                                      AS obsEdit,
       L.quant                                                                    AS litros

FROM sqldados.inv           AS I
  INNER JOIN sqldados.store AS S
	       ON S.no = I.storeno
  LEFT JOIN  sqldados.invxa AS X
	       USING (invno)
  LEFT JOIN  sqldados.T_LITROS       AS L
	       USING (invno)
WHERE I.bits & POW(2, 4) = 0
  AND I.auxShort13 & POW(2, 15) = 0
  AND I.vendno = :vendno
  AND (I.storeno = :loja OR :loja = 0)
GROUP BY I.invno
