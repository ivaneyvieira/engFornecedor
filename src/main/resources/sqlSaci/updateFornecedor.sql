UPDATE sqldados.inv
SET c9  = :observacao,
    s28 = :status
WHERE vendno = :vendno
  AND (storeno = :storeno OR :storeno = 0);


UPDATE sqldados.vend
SET c1 = :observacao,
    s8 = :status
WHERE no = :vendno
  AND (:storeno = 0)