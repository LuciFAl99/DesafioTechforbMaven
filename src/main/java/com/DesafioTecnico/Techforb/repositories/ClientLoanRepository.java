package com.DesafioTecnico.Techforb.repositories;

import com.DesafioTecnico.Techforb.models.Client;
import com.DesafioTecnico.Techforb.models.ClientLoan;
import com.DesafioTecnico.Techforb.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository <ClientLoan, Long> {
    ClientLoan findByLoanAndClient(Loan loan, Client client);
}
