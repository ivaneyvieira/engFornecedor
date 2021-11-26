UPDATE sqldados.inv
SET c9  = :observacao,
    s28 = :status
WHERE vendno = :vendno
  AND (storeno = :storeno OR :storeno = 0)