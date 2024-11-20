package com.mindex.challenge.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.exception.InvalidRequestArgumentException;
import com.mindex.challenge.exception.RequestNotFoundException;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    
        private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

        private final EmployeeRepository employeeRepository;

        @Autowired
        public ReportingStructureServiceImpl(final EmployeeRepository employeeRepository) {
            this.employeeRepository = employeeRepository;
        }

        /**
         * Validates id input and if the given employee id matches with an employee saved in our db.
         * If validations pass, calls to calculate number of reports for the domain Employee and return constructed object
         * with Employee and Number of Reports.
         */
        @Override
        public ReportingStructure create(String employeeId) {
            LOG.debug("Attempting to create Reporting Structure for employee {}", employeeId);

            if(employeeId == null || employeeId == "") {
                throw new InvalidRequestArgumentException(String.format("Employee id: %s must not be null or have an empty value.", employeeId));
            }

            Employee employee = employeeRepository.findByEmployeeId(employeeId);
            
            if(employee == null) {
                throw new RequestNotFoundException(String.format("No employee found for employee: %s", employeeId));
            }

            LOG.debug("Creating Reporting Structure for employee {}", employeeId);

            ReportingStructure reportingStructure = new ReportingStructure();
            int numberOfReports = getNumberOfReportsFromEmployee(employee);

            reportingStructure.setEmployee(employee);
            reportingStructure.setNumberOfReports(numberOfReports);

            return reportingStructure;
        }

        /**
         * For a given Employee and their prospective direct reports,
         * count the direct reports directly for the given employee.
         * 
         * Recursively count the indirect reports and return number of
         * reports for the given Employee.
         * @param employee
         */
        private int getNumberOfReportsFromEmployee(Employee employee) {
            
            List<Employee> directReports = employee.getDirectReports();

            // This employee has no direct reports. Return zero.
            if(directReports == null || directReports.isEmpty()) {
                return 0;
            }

            int numberOfReports = directReports.size();

            // Recursive call to obtain indirect reports
            for(Employee directReport : directReports) {
                numberOfReports += getNumberOfReportsFromEmployee(directReport);
            }

            return numberOfReports;

        }


}
