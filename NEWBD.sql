create database machti_ImportExport


use machti_ImportExport


create table Fournisseur(
idFournisseur int primary key identity,
nomFournisseur nvarchar(100),
adresse nvarchar(255),
telephone varchar(15) 
)


create table Produit (
refProduit int primary key identity ,
LibelleProduit nvarchar(100),
typeProduit varchar(50) check (typeProduit in ('Produit agricole','Machine') ),
prix_Unitaire float ,
qte_stock int,
idFournisseur int foreign key references fournisseur (idFournisseur)
)

create table client(
idClient int primary key identity,
nom nvarchar(150),
adresse nvarchar(255),
telephone varchar(15) 
)

alter table client 
add ville nvarchar(50)

create table Commande (
idCommande int primary key,
idClient int foreign key references client (idClient),
etat_Commande varchar(50) check (etat_commande in ('En cours','livrée','annulée') ),
)

alter table commande 
add dateCommande date default getdate()

alter table commande 
add TotalHT float 

create table ligneCommande(
refProduit int foreign key references produit (refProduit),
idCommande int foreign key references commande(idCommande),
qte int ,
Constraint PK_ligneCommande primary key (refProduit, idCommande) 
)


select * from Fournisseur

drop table Fournisseur

insert into Fournisseur values ('KhalilNajmi SARL','El aioun sidi mellouck','0627860225'),
								('YoussefElkh SARL','Oujda-angad','0627860225'),
								('SalmiAbdou SARL','Sidi bouzekri','0627860225')

select * from Produit

insert into Produit values ('Pommes','Produit agricole',150, 100, 1),
							('Machine tesla','Machine',12000, 10, 1),
							('Fleurs','Produit agricole',25, 1000, 2),
							('Machine dressage','Machine',8500, 50, 2),
							('Oranges','Produit agricole',120, 100, 3),
							('Tracteur','Machine',159000, 15, 3)



select * from client 

insert into client values ('Youssef el khalloufi','Rabat','0627860225','Maroc'),
							('Khalil najmi','Lyon','0627860225','France'),
							('Abdou salmi','Seville','0627860225','Espagne')

						



----------------------------------Trigger pour la mise a jour du stock -------------------------
create trigger tgr_updateStock on ligneCommande after insert, update 
as
begin
	declare @qte int;
	select @qte = qte from inserted ;

	update Produit 
	set qte_stock -= @qte 
	where refProduit = (select refProduit from inserted );
end

---------------------------------Procedure pour linsertion dune commande----------------------------
create procedure dbo.insererCommande(@idCmd as int, @idClient as int, @refProduit as int, @qte as int)
as
begin
	if exists (select * from client where idClient = @idClient ) 
		begin
			if exists ( select * from Produit where refProduit = @refProduit ) 
				begin
					
					declare @qteStock int ;
					select @qteStock = qte_Stock from produit where refProduit = @refProduit ;

					if @qteStock >= @qte 
						begin
							if not exists ( select * from commande where idCommande = @idCmd )
								begin
									insert into commande (idCommande, idClient, etat_Commande) 
									values (@idCmd, @idClient, 'en cours');
								end
							insert into ligneCommande (refProduit, idCommande, qte)
							values (@refProduit, @idCmd, @qte) ;
						end
					else
						begin
							print 'Quantite non disponible' ;
						end
				end
			else
				begin
					print 'Produit nexiste pas' ;
				end
		end
	else 
		begin
			print 'Client nexiste pas'
		end
end

drop procedure insererCommande

exec  dbo.insererCommande 
		@idCmd     =	'3',
		@idClient  =	'3',
		@refProduit=	'5',
		@qte	   =	'1' 



select * from Commande
select * from ligneCommande
select * from Produit
select * from client
select * from fournisseur
insert into fournisseur values ('sadki','','')

delete from fournisseur where idFournisseur = 5
select count(*) from fournisseur

update client
set adresse = 'Sanitiago bernabeau', ville = 'Madrid', pays = 'Espagne' 
where idClient = 3

----------------------------Triggrt pour la mise a jour du totalHT------------------------------------
create trigger tgr_CalculTotalHT on ligneCommande after insert, update
as
begin
	declare @totalHT float ;
	declare @idCmd int ;

	
	select @idCmd = idCommande from inserted ;
	select @totalHT = sum(p.prix_unitaire * lc.qte) from produit p, ligneCommande lc 
						where p.refProduit = lc.refProduit and idCommande = @idCmd ;

	update commande 
	set totalHT = @totalHT 
	where idCommande = @idCmd
end

drop trigger tgr_CalculTotalHT




select * from commande


select clt.nom, c.idCommande, p.libelleproduit, c.etat_Commande, c.dateCommande  from commande c , lignecommande lc, produit p, Client clt
where c.idCommande = lc.idCommande and lc.refProduit = p.refProduit and c.idClient = clt.idclient


select c.idCommande, p.libelleproduit, c.etat_Commande, c.dateCommande  from commande c , lignecommande lc, produit p, Client clt
where c.idCommande = lc.idCommande and lc.refProduit = p.refProduit and c.idClient = clt.idclient and clt.nom = 'abdou salmi'


----------------------------Client et nombre de produit commandé--------------------------------------
select clt.nom as client, c.idCommande as [N° commande], 
count(libelleproduit) as [nombre de produit commandé] ,
c.etat_Commande as [Etat commande], c.dateCommande as [Date commande], c.totalHT as [Total HT] from commande c ,
lignecommande lc, produit p, Client clt
where c.idCommande = lc.idCommande and lc.refProduit = p.refProduit 
and c.idClient = clt.idclient 
group by clt.nom, c.idCommande, c.etat_commande, c.dateCommande, c.TotalHT



---------------------------------Produit par Commande --------------------------------------------
select p.refProduit ,p.libelleProduit, p.typeProduit, p.prix_Unitaire, lc.qte from produit p, ligneCommande lc 
where p.refProduit = lc.refProduit and lc.idCommande = 3 and p.refProduit = 2

select * from ligneCommande where idCommande = 3
select idClient from commande where idCommande = 3
select * from commande


select c.idClient as [Id client], c.nom as [Nom], c.ville as [Ville], c.pays as [Pays],
cmd.etat_Commande as [Etat Commande], cmd.dateCommande as [Date Commande], cmd.TotalHT as [Total HT]
from client c, commande cmd 
where c.idClient = cmd.idClient and c.idClient = 1 and cmd.idCommande = 1

select * from produit
select * from commande
select * from ligneCommande

----------------------------------rapport par produit sans periode-------------------------------------
select p.refProduit, p.LibelleProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c
where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande
group by p.refProduit, p.typeProduit, p.prix_Unitaire, p.LibelleProduit

-----------------------------------rapport par produit (periode)-------------------------------------
select p.refProduit, p.libelleProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c
where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande and c.dateCommande between '06-10-2024' and '06-14-2024'
group by p.refProduit, p.typeProduit, p.prix_Unitaire, p.libelleProduit

----------------------------------rapport par produit(type, periode)-------------------------------------
select p.refProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c
where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande and c.dateCommande 
between '06-10-2024' and '06-15-2024' and p.typeProduit ='Machine'
group by p.refProduit, p.typeProduit, p.prix_Unitaire

--------------------------------------rapport par produit(type)-------------------------------------
select p.refProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c
where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande and p.typeProduit = 'Machine'
group by p.refProduit, p.typeProduit, p.prix_Unitaire