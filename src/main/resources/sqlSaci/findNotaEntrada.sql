SELECT I.storeno                                                                  AS loja,
       S.otherName                                                                AS siglaLoja,
       CAST(I.issue_date AS DATE)                                                 AS dataNF,
       CAST(CONCAT(I.nfname, IF(I.invse = '', '', CONCAT('/', I.invse))) AS CHAR) AS nota,
       grossamt / 100                                                             AS valor,
       I.remarks                                                                  AS obs,
       I.vendno                                                                   AS vendno
FROM sqldados.inv           AS I
  INNER JOIN sqldados.store AS S
	       ON S.no = I.storeno
WHERE I.bits & POW(2, 4) = 0
  AND I.auxShort13 & POW(2, 15) = 0
  AND I.vendno = :vendno